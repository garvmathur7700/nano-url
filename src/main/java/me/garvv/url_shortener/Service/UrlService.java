package me.garvv.url_shortener.Service;

import me.garvv.url_shortener.DTO.UrlRedirectionResponseDTO;
import me.garvv.url_shortener.DTO.UrlShortenResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UrlService {
    public UrlShortenResponseDTO createNewShortUrl (String longUrl);
    public UrlRedirectionResponseDTO getLongUrl (String shortUrl);
}
