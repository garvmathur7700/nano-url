package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UrlShortenRequestDTO (
        @NotEmpty(message = "longUrl is required")
        @NotBlank(message = "longUrl is required")
        String longUrl
){}