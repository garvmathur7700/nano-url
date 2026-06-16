package me.garvv.url_shortener.Service;

import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private UrlService urlService;

    @Test
    public void UrlRepository_ValidLongUrl_ReturnsUrlShortenResponse() {
        // Arrange
        String longUrl = "    google.com   ";
        String sanitizedLongUrl = "https://www.google.com";
        String shortUrl = "abc12";
        long generatedRandomNumber = 12345L;

        when(urlRepository.existsByLongUrl(sanitizedLongUrl)).thenReturn(false);

        when(randomNumber.getRandomLong()).thenReturn(generatedRandomNumber);

        when(base62.encode(generatedRandomNumber)).thenReturn(shortUrl);

        when(urlRepository.existsByShortUrl(shortUrl)).thenReturn(false);

        // Act


        // Assert
    }
}
