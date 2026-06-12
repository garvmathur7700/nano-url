package me.garvv.url_shortener.Repository;

import me.garvv.url_shortener.Model.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class UrlRepositoryTests {

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void UrlRepository_ExistsByLongUrl_ReturnsBoolean() {
        // Arrange
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        // Act
        boolean resultForValidLongUrl = urlRepository.existsByLongUrl(longUrl);
        boolean resultForInvalidLongUrl = urlRepository.existsByLongUrl("abc");

        // Assert
        Assertions.assertTrue(resultForValidLongUrl);
        Assertions.assertFalse(resultForInvalidLongUrl);
    }

    @Test
    public void UrlRepository_ExistsByShortUrl_ReturnsBoolean() {
        // Arrange
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        // Act
        boolean resultForValidLongUrl = urlRepository.existsByShortUrl(shortUrl);
        boolean resultForInvalidLongUrl = urlRepository.existsByShortUrl("abc");

        // Assert
        Assertions.assertTrue(resultForValidLongUrl);
        Assertions.assertFalse(resultForInvalidLongUrl);
    }

    @Test
    public void UrlRepository_FindShortUrlByLongUrl_ReturnsShortUrl() {
        // Arrange
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        // Act
        String resultForValidLongUrl = urlRepository.findShortUrlByLongUrl(longUrl);
        String resultForInvalidLongUrl = urlRepository.findShortUrlByLongUrl("abc");

        // Assert
        Assertions.assertEquals(shortUrl, resultForValidLongUrl);
        Assertions.assertNull(resultForInvalidLongUrl);
    }


    @Test
    public void UrlRepository_FindLongUrlByShortUrl_ReturnsLongUrl() {
        // Arrange
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        // Act
        Optional<String> resultForValidShortUrl = urlRepository.findLongUrlByShortUrl(shortUrl);
        Optional<String> resultForInvalidShortUrl = urlRepository.findLongUrlByShortUrl("abc");

        // Assert
        Assertions.assertEquals(Optional.of(longUrl), resultForValidShortUrl);
        Assertions.assertEquals(Optional.empty(), resultForInvalidShortUrl);
    }
}
