package me.garvv.url_shortener.Service;

import jakarta.transaction.Transactional;
import me.garvv.url_shortener.DTO.UrlRedirectionResponseDTO;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import me.garvv.url_shortener.Model.Url;
import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
import me.garvv.url_shortener.exceptions.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public UrlShortenResponseDTO createNewShortUrl (String longUrl) {
        // Step 1: If 'longUrl' already exists in DB
            // return 'shortUrl' corresponding to the 'longUrl'
        if (urlRepository.existsByLongUrl(longUrl)) {
            String existingShortUrl = urlRepository.findShortUrlByLongUrl(longUrl);
            return new UrlShortenResponseDTO(existingShortUrl);
        }

        // Step 2: Generate 'shortUrl' by random integer and encoding
        String shortUrl = Base62.encode(SecureRandomNumberGenerator.getRandomLong());

        // Step 3: Save the generated 'shortUrl', 'longUrl' to the DB
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        return new UrlShortenResponseDTO(url.getShortUrl());
    }

    @Transactional
    public UrlRedirectionResponseDTO getLongUrl (String shortUrl) {
        String longUrl = urlRepository
                .findLongUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));

        return new UrlRedirectionResponseDTO(shortUrl, longUrl);
    }
}
