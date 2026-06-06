package me.garvv.url_shortener.Service;

import jakarta.transaction.Transactional;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import me.garvv.url_shortener.Model.Url;
import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
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
    public ResponseEntity<UrlShortenResponseDTO> createNewShortUrl (String longUrl) {
        // Step 1: If 'longUrl' already exists in DB
            // return 'shortUrl' corresponding to the 'longUrl'
        if (urlRepository.existsByLongUrl(longUrl)) {
            String existingShortUrl = urlRepository.findShortUrlByLongUrl(longUrl);
            UrlShortenResponseDTO resDto = new UrlShortenResponseDTO(existingShortUrl);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(resDto);
        }

        // Step 2: Generate 'shortUrl' by random integer and encoding
        String shortUrl = Base62.encode(SecureRandomNumberGenerator.getRandomLong());
        System.out.println(shortUrl);

        // Step 3: Save the generated 'shortUrl', 'longUrl' to the DB
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        UrlShortenResponseDTO response = new UrlShortenResponseDTO(url.getShortUrl());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
