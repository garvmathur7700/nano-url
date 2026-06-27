package me.garvv.url_shortener.Service;

import me.garvv.url_shortener.DTO.UrlRedirectionResponseDTO;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import me.garvv.url_shortener.Model.Url;
import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
import me.garvv.url_shortener.exceptions.RequestTimedOutException;
import me.garvv.url_shortener.exceptions.UrlNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTests {
    @Mock
    private UrlRepository urlRepository;

    @Mock
    private SecureRandomNumberGenerator randomNumber;

    @Mock
    private Base62 base62;

    @InjectMocks
    private UrlServiceImpl urlService;

    @Value("${app.base-url")
    private String baseUrl;

    @Test
    public void UrlService_createNewShortUrl_givenValidLongUrl_shouldReturnUrlShortenResponse() {
        // Arrange
        String validLongUrl = "https://www.google.com";
        String generatedShortUrl = "Abc123";

        when(urlRepository.existsByLongUrl(validLongUrl)).thenReturn(false);
        when(randomNumber.getRandomLong()).thenReturn(123456L);
        when(base62.encode(123456L)).thenReturn(generatedShortUrl);
        when(urlRepository.existsByShortUrl(generatedShortUrl)).thenReturn(false);

        String fullShortUrl = baseUrl + "/" + generatedShortUrl;

        UrlShortenResponseDTO expectedResponseDTO = new UrlShortenResponseDTO(fullShortUrl, validLongUrl);

        // Act
        UrlShortenResponseDTO actualResponseDTO = urlService.createNewShortUrl(validLongUrl);

        // Assert
        Assertions.assertEquals(expectedResponseDTO, actualResponseDTO);

        // Verify
        verify(urlRepository, times(1))
                .save(any(Url.class));
    }

    @Test
    public void UrlService_InvalidLongUrl_ThrowsIllegalArgumentException() {
        // Arrange
        String invalidLongUrl = "https://www.google.com        youtube.com";

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> urlService.createNewShortUrl(invalidLongUrl));
    }

    @Test
    public void UrlService_ValidLongUrlAlreadyExists_ReturnsUrlShortenResponseWithExistingLongUrl() {
        // Arrange
        String validExistingLongUrl = "https://www.google.com";
        String existingShortUrl = "Abc123";

        when(urlRepository.existsByLongUrl(validExistingLongUrl)).thenReturn(true);
        when(urlRepository.findShortUrlByLongUrl(validExistingLongUrl)).thenReturn(existingShortUrl);

        UrlShortenResponseDTO expectedResponseDTO = new UrlShortenResponseDTO(existingShortUrl, validExistingLongUrl);

        // Act
        UrlShortenResponseDTO actualResponseDTO = urlService.createNewShortUrl(validExistingLongUrl);

        // Assert
        Assertions.assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    public void UrlService_ShortUrlAlreadyExistsAndReachesMaxTries_ReturnsRequestTimedOutException() {
        // Arrange
        String longUrl = "https://www.google.com";
        String existingShortUrl = "Abc123";

        when(urlRepository.existsByLongUrl(longUrl)).thenReturn(false);
        when(randomNumber.getRandomLong()).thenReturn(123456L);
        when(base62.encode(123456L)).thenReturn(existingShortUrl);
        when(urlRepository.existsByShortUrl(existingShortUrl)).thenReturn(true);

        // Act & Assert
        Assertions.assertThrows(
                RequestTimedOutException.class,
                () -> urlService.createNewShortUrl(longUrl));

        // Verify
        verify(urlRepository, times(5))
                .existsByShortUrl(existingShortUrl);
    }

    @Test
    public void UrlService_ShortUrlAlreadyExistsButDoesNotReachMaxTries() {
        // Arrange
        String longUrl = "https://www.google.com";
        String existingShortUrl = "Abc123";

        when(urlRepository.existsByLongUrl(longUrl)).thenReturn(false);
        when(randomNumber.getRandomLong()).thenReturn(123456L);
        when(base62.encode(123456L)).thenReturn(existingShortUrl);
        when(urlRepository.existsByShortUrl(existingShortUrl)).thenReturn(true, true, false);

        String fullShortUrl = baseUrl + "/" + existingShortUrl;

        UrlShortenResponseDTO expectedResponseDTO = new UrlShortenResponseDTO(fullShortUrl, longUrl);

        // Act
        UrlShortenResponseDTO actualResponseDTO = urlService.createNewShortUrl(longUrl);

        // Assert
        Assertions.assertEquals(expectedResponseDTO, actualResponseDTO);

        // Verify
        verify(urlRepository, times(3))
                .existsByShortUrl(existingShortUrl);
    }

    @Test
    public void UrlService_ExistingShortUrl_ReturnsUrlRedirectionResponse() {
        // Arrange
        String longUrl = "https://www.google.com";
        String shortUrl = "Abc123";

        when(urlRepository.findLongUrlByShortUrl(shortUrl)).thenReturn(Optional.of(longUrl));

        String fullShortUrl = baseUrl + "/" + shortUrl;

        UrlRedirectionResponseDTO expectedResponseDTO = new UrlRedirectionResponseDTO(fullShortUrl, longUrl);

        // Act
        UrlRedirectionResponseDTO actualResponseDTO = urlService.getLongUrl(shortUrl);

        // Assert
        Assertions.assertEquals(expectedResponseDTO, actualResponseDTO);

        // Verify
        verify(urlRepository, times(1))
                .findLongUrlByShortUrl(shortUrl);
    }

    @Test
    public void UrlService_NonExistingShortUrl_ThrowsUrlNotFoundException() {
        // Arrange
        String shortUrl = "Abc123";

        when(urlRepository.findLongUrlByShortUrl(shortUrl))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                UrlNotFoundException.class,
                () -> urlService.getLongUrl(shortUrl));

        // Verify
        verify(urlRepository, times(1))
                .findLongUrlByShortUrl(shortUrl);
    }
}
