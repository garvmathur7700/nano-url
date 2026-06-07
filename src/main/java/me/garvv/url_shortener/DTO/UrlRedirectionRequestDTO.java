package me.garvv.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;

public record UrlRedirectionRequestDTO(
        @NotBlank(message = "shortUrl is required")
        String shortUrl
) {}
