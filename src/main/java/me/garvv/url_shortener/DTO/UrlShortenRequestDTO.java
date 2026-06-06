package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UrlShortenRequestDTO (
        @NotBlank(message = "longUrl is required")
        String longUrl
){}