package me.garvv.url_shortener.Utils;

import org.springframework.stereotype.Component;

@Component
public class Base62 {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = CHARS.length();

    private Base62() {}

    public String encode (long num) {
        if (num < 0)
            throw new IllegalArgumentException("Input has to be greater than zero (0)");

        if (num == 0)
            return "000000";

        StringBuilder code = new StringBuilder();

        while (num > 0) {
            int remainder = (int) (num % BASE);
            code.append(CHARS.charAt(remainder));
            num /= BASE;
        }

        return code.reverse().toString();
    }
}
