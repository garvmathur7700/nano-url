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
    public void existsByLongUrlTest() {
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);

        Assertions.assertEquals(true, urlRepository.existsByLongUrl(longUrl));
        Assertions.assertEquals(false, urlRepository.existsByLongUrl("abc"));
    }

    @Test
    public void existsByShortUrlTest() {
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);

        Assertions.assertEquals(true, urlRepository.existsByShortUrl(shortUrl));
        Assertions.assertEquals(false, urlRepository.existsByShortUrl("abc"));
    }

    @Test
    public void findShortUrlByLongUrlTest() {
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);

        Assertions.assertEquals(shortUrl, urlRepository.findShortUrlByLongUrl(longUrl));
        Assertions.assertNull(urlRepository.findShortUrlByLongUrl("abc"));
    }


    @Test
    public void findLongUrlByShortUrlTest() {
        String longUrl = "https://www.google.com";
        String shortUrl = "abcdef";

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);

        Assertions.assertEquals(Optional.of(longUrl), urlRepository.findLongUrlByShortUrl(shortUrl));
        Assertions.assertEquals(Optional.empty(), urlRepository.findLongUrlByShortUrl("abc"));
    }
}
