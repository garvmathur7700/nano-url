package me.garvv.url_shortener.Controller;

import me.garvv.url_shortener.DTO.UrlRedirectionResponseDTO;
import me.garvv.url_shortener.Service.UrlServiceImpl;
import me.garvv.url_shortener.exceptions.UrlNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UrlController.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UrlControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlServiceImpl urlService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * GET `getLongUrl` method tests start here
     */

    @Test
    public void UrlController_getLongUrl_givenValidShortUrl_shouldReturn302Redirect() throws Exception {
        // Arrange
        String longUrl = "https://www.google.com";
        String shortUrl = "Abc123";
        UrlRedirectionResponseDTO responseDTO = new UrlRedirectionResponseDTO(shortUrl, longUrl);

        when(urlService.getLongUrl(shortUrl)).thenReturn(responseDTO);

        // Act
        mockMvc.perform(get("/api/url/{shortUrl}", shortUrl))

        // Assert
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(longUrl));
    }

    @Test
    public void UrlController_getLongUrl_givenUnknownShortUrl_shouldReturn404() throws Exception {
        // Arrange
        String shortUrl = "abcdef";
        when(urlService.getLongUrl(shortUrl))
                .thenThrow(new UrlNotFoundException("URL not found"));

        // Act
        mockMvc.perform(get("/api/url/{shortUrl}", shortUrl))

        // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void UrlController_getLongUrl_givenInvalidShortUrl_shouldReturn400() throws Exception {
        // Arrange
        String shortUrl = "abc";

        // Act
        mockMvc.perform(get("/api/url/{shortUrl}", shortUrl))

                // Assert
                .andExpect(status().isBadRequest());
    }

    /***********************************************************/

    /**
     * POST `createShortUrl test start here
     */

    @Test
    public void UrlController_createShortUrl_given
}
