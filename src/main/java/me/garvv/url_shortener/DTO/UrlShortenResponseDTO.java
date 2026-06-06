package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UrlShortenResponseDTO(
        @NotBlank(message = "shortUrl is required")
        String shortUrl
) {}
