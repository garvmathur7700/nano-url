package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;

public record UrlShortenRequestDTO (
        @NotBlank(message = "longUrl is required")
        String longUrl
){}