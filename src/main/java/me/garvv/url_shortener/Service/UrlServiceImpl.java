package me.garvv.url_shortener.Service;

import jakarta.transaction.Transactional;
import me.garvv.url_shortener.DTO.UrlRedirectionResponseDTO;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import me.garvv.url_shortener.Model.Url;
import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
import me.garvv.url_shortener.Utils.UrlUtils;
import me.garvv.url_shortener.exceptions.RequestTimedOutException;
import me.garvv.url_shortener.exceptions.UrlNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    private final SecureRandomNumberGenerator randomNumber;

    private final Base62 base62;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlServiceImpl(UrlRepository urlRepository, SecureRandomNumberGenerator randomNumber, Base62 base62) {
        this.urlRepository = urlRepository;
        this.randomNumber = randomNumber;
        this.base62 = base62;
    }

    @Transactional
    public UrlShortenResponseDTO createNewShortUrl(String longUrl) {
        // Step 1: Sanitize 'longUrl'
        String sanitizedLongUrl = UrlUtils.sanitize(longUrl);

        // Step 2: Validate 'sanitizedLongUrl'
        if (!UrlUtils.validate(sanitizedLongUrl))
            throw new IllegalArgumentException("Not a valid URL.");

        // Step 3: If 'sanitizedLongUrl' already exists in DB
        // return 'shortUrl' corresponding to the 'sanitizedLongUrl'
        if (urlRepository.existsByLongUrl(sanitizedLongUrl)) {
            String existingShortUrl = urlRepository.findShortUrlByLongUrl(sanitizedLongUrl);
            return new UrlShortenResponseDTO(existingShortUrl, sanitizedLongUrl);
        }

        // Step 4: Generate 'shortUrl' by random integer and encoding
        // Keep generating 'shortUrl' if it is found in DB, in other words
        // keep generating 'shortUrl' while it exists in DB
        String shortUrl;
        int urlRegenerationCounter = 0;
        int maxTries = 5;
        do {
            shortUrl = base62.encode(randomNumber.getRandomLong());
            urlRegenerationCounter++;
        } while (urlRepository.existsByShortUrl(shortUrl) && urlRegenerationCounter < maxTries);

        if (urlRegenerationCounter == maxTries)
            throw new RequestTimedOutException("Database operation failed after " + urlRegenerationCounter + " tries!");

        // Step 5: Save the generated 'shortUrl', 'sanitizedLongUrl' to the DB
        Url url = new Url();
        url.setLongUrl(sanitizedLongUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        // Step 6: Create the directable 'shortUrl'
        String fullShortUrl = baseUrl + "/" + shortUrl;

        return new UrlShortenResponseDTO(fullShortUrl, sanitizedLongUrl);
    }

    @Transactional
    public UrlRedirectionResponseDTO getLongUrl(String shortUrl) {
        String longUrl = urlRepository
                .findLongUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));

        // Step 6: Create the directable 'shortUrl'
        String fullShortUrl = baseUrl + "/" + shortUrl;

        return new UrlRedirectionResponseDTO(fullShortUrl, longUrl);
    }
}
