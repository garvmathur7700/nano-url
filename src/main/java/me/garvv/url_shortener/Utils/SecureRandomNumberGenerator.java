package me.garvv.url_shortener.Utils;

import java.security.SecureRandom;

public class SecureRandomNumberGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final long maxLimit = 56_800_235_584L;

    public static long getRandomLong() {
        // 56_800_235_584
        long random = secureRandom.nextLong(maxLimit);

        return random;
    }
}
