package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;

public record UrlRedirectionResponseDTO (
    @NotBlank(message = "shortUrl is required")
    String shortUrl,
    @NotBlank(message = "longUrl unavailable")
    String longUrl
) {}