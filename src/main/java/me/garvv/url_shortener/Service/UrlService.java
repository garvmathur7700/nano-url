package me.garvv.url_shortener.Service;

import jakarta.transaction.Transactional;
import me.garvv.url_shortener.Model.Url;
import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public String shortenUrl (String longUrl) {
        // Generate shortUrl
        String shortUrl = Base62.encode(SecureRandomNumberGenerator.getRandomLong());

        // fetch longUrl from DB, `null` if not exists
        Optional<String> longUrlInRecord = urlRepository.findLongUrlByShortUrl(shortUrl);

        // collision handling:
        if (longUrlInRecord.isPresent()) {

            // if shortUrl and longUrl match, return same shortUrl
            if (longUrlInRecord.equals(Optional.of(longUrl)))
                return shortUrl;

            // if shortUrl matches but not longUrl, then re-encode
            else
                shortenUrl(longUrl);
        }

        // Else, save shortUrl, longUrl, createdAt to repository
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);

        return url.getShortUrl();
    }
}
