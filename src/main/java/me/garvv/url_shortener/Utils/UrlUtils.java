package me.garvv.url_shortener.Utils;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtils {
    public static String sanitize (String url) {
        // Step 1: If 'url' is null or empty, return empty string ("")
        if (url == null || url.trim().isEmpty())
            return "";

        // Step 2: If 'url' does not have 'http:// OR https://' as prefix, then add 'https://'
        String cleanUrl = url.trim();

        if (!cleanUrl.matches("^(?i)(http|https)://.*$"))
            cleanUrl = "https://" + url;

        if (!cleanUrl.matches("^(?i)(http|https)://www[.].*$"))
            cleanUrl = cleanUrl.replaceFirst("://", "://www.");

        // Step 3: Return sanitized 'url'
        return cleanUrl;
    }

    public static Boolean validate (String url) {
        try {
            // Step 1: Convert the String 'url' into a Java 'URI' object
            URI uri = new URI(url);

            // A valid URI always has a scheme (http or https) and host (google.com)
            // Step 2: Return 'true', if scheme isn't null and equal to 'http' or 'https'
            return uri.getScheme() != null &&
                    uri.getHost() != null &&
                    (uri.getScheme().equalsIgnoreCase("http") ||
                            uri.getScheme().equalsIgnoreCase("https"));

        } catch (URISyntaxException e) {
            // If link is invalid, return false
            return false;
        }
    }
}
