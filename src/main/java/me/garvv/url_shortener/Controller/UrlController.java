package me.garvv.url_shortener.Controller;

import jakarta.validation.Valid;
import me.garvv.url_shortener.DTO.UrlShortenRequestDTO;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import me.garvv.url_shortener.Service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlShortenResponseDTO> shorten(@Valid @RequestBody UrlShortenRequestDTO request) {
        return urlService.createNewShortUrl(request.longUrl());
    }
}
