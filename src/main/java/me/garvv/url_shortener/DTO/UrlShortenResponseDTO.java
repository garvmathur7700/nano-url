package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;

public record UrlShortenResponseDTO(
        @NotBlank(message = "shortUrl is required")
        String shortUrl
) {}
