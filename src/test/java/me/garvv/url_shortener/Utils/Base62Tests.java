package me.garvv.url_shortener.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base62Tests {
    @Test
    public void Base62_encode_givenValidLongInteger_shouldReturnString() {
        Base62 base62 = new Base62();

        Assertions.assertEquals(
                base62.encode(12345678901L),
                "DTVD3F"
        );
    }

    @Test
    public void Base62_encode_givenOne_shouldReturnString() {
        Base62 base62 = new Base62();

        Assertions.assertEquals(
                base62.encode(1),
                "1"
        );
    }

    @Test
    public void Base62_encode_givenTen_shouldReturnString() {
        Base62 base62 = new Base62();

        Assertions.assertEquals(
                base62.encode(10),
                "A"
        );
    }

    @Test
    public void Base62_encode_givenMaxValidLongInteger_shouldReturnString() {
        Base62 base62 = new Base62();

        Assertions.assertEquals(
                base62.encode(56_800_235_583L),
                "zzzzzz"
        );
    }
}
