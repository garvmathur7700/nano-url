package me.garvv.url_shortener.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlUtilsTests {

    /**
     * 'sanitize' tests start here
     */

    @Test
    public void UrlUtils_sanitize_givenSanitizedString_shouldReturnInputString() {
        String sanitizedString = "https://www.google.com";
        String inputString = "https://www.google.com";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenStringWithSpace_shouldReturnStringWithNoSpaces() {
        String sanitizedString = "https://www.google.com";
        String inputString = "        https://www.google.com      ";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenStringWithoutHttpOrHttps_shouldReturnWithHttps() {
        String sanitizedString = "https://www.google.com";
        String inputString = "www.google.com";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenStringWithoutWww_shouldReturnWithWww() {
        String sanitizedString = "https://www.google.com";
        String inputString = "https://google.com";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenStringWithoutWwwAndHttpsOrHttp_shouldReturnWithWwwAndHttps() {
        String sanitizedString = "https://www.google.com";
        String inputString = "google.com";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenEmptyString_shouldReturnEmptyString() {
        String sanitizedString = "";
        String inputString = "";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenUppercaseString_shouldReturnLowercaseString() {
        String sanitizedString = "https://www.google.com";
        String inputString = "HTTPS://WWW.GOOGLE.COM";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    @Test
    public void UrlUtils_sanitize_givenMixedcaseString_shouldReturnLowercaseString() {
        String sanitizedString = "https://www.google.com";
        String inputString = "HTtPs://www.GoOGle.CoM";

        Assertions.assertEquals(sanitizedString, UrlUtils.sanitize(inputString));
    }

    /*********************************************************/

    /**
     * 'validate' tests start here
     */

    @Test
    public void UrlUtils_validate_givenValidUrlScheme_shouldReturnTrue() {
        Assertions.assertTrue(UrlUtils.validate("https://www.google.com"));
        Assertions.assertTrue(UrlUtils.validate("http://www.google.com"));
    }

    @Test
    public void UrlUtils_validate_givenUrlWithoutHost_shouldReturnFalse() {
        String inputString = "https://";

        Assertions.assertFalse(UrlUtils.validate(inputString));
    }

    @Test
    public void UrlUtils_validate_givenUrlWithoutScheme_shouldReturnFalse() {
        String inputString = "google.com";

        Assertions.assertFalse(UrlUtils.validate(inputString));
    }

    @Test
    public void UrlUtils_validate_givenWrongScheme_shouldReturnFalse() {
        String inputString = "ftp://google.com";

        Assertions.assertFalse(UrlUtils.validate(inputString));
    }

    @Test
    public void UrlUtils_validate_givenEmptyUrl_shouldReturnFalse() {
        String inputString = "";

        Assertions.assertFalse(UrlUtils.validate(inputString));
    }
}
