package me.garvv.url_shortener.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SecureRandomNumberGeneratorTests {
    @Test
    public void RandomNumber_getRandomLong_testRange_shouldReturnWithinRange() {
        SecureRandomNumberGenerator rn = new SecureRandomNumberGenerator();

        for (int i = 0; i < 1000; i++) {
            long num = rn.getRandomLong();

            Assertions.assertTrue(num >= 0);
            Assertions.assertTrue(num < 56_800_235_583L);
        }
    }
}
