package me.garvv.url_shortener.Service;

import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UrlService {
    public ResponseEntity<UrlShortenResponseDTO> createNewShortUrl (String longUrl);
}
