package me.garvv.url_shortener.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import me.garvv.url_shortener.DTO.UrlRedirectionRequestDTO;
import me.garvv.url_shortener.DTO.UrlRedirectionResponseDTO;
import me.garvv.url_shortener.DTO.UrlShortenRequestDTO;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import me.garvv.url_shortener.Service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlShortenResponseDTO> createShortUrl(@Valid @RequestBody UrlShortenRequestDTO request) {
        UrlShortenResponseDTO responseDTO = urlService.createNewShortUrl(request.longUrl());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getLongUrl(
            @PathVariable
            @NotBlank
            @Size(min = 6, max = 6)
            String shortUrl) {
        String longUrl = urlService
                .getLongUrl(shortUrl)
                .longUrl();

        URI redirectionURI = URI.create(longUrl);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(redirectionURI)
                .build();
    }
}
