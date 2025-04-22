
package com.couchbase.client.core.time;

import org.junit.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class FixedDelayTest {

    @Test
    public void shouldCalculateFixedDelay() {
        Delay fixedDelay = new FixedDelay(3, TimeUnit.SECONDS);

        assertEquals(3, fixedDelay.calculate(1));
        assertEquals(3, fixedDelay.calculate(2));
        assertEquals(3, fixedDelay.calculate(3));
    }

}
