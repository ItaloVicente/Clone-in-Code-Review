
package com.couchbase.client.core.time;

import org.junit.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class LinearDelayTest {

    @Test
    public void shouldCalculateLinearly() {
        Delay linearDelay = new LinearDelay(1, TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 1);

        assertEquals(1, linearDelay.calculate(1));
        assertEquals(2, linearDelay.calculate(2));
        assertEquals(3, linearDelay.calculate(3));
        assertEquals(4, linearDelay.calculate(4));
    }

    @Test
    public void shouldRespectLowerBound() {
        Delay linearDelay = new LinearDelay(1, TimeUnit.SECONDS, Integer.MAX_VALUE, 3, 1);

        assertEquals(3, linearDelay.calculate(1));
        assertEquals(3, linearDelay.calculate(2));
        assertEquals(3, linearDelay.calculate(3));
        assertEquals(4, linearDelay.calculate(4));
    }

    @Test
    public void shouldRespectUpperBound() {
        Delay linearDelay = new LinearDelay(1, TimeUnit.SECONDS, 2, 0, 1);

        assertEquals(1, linearDelay.calculate(1));
        assertEquals(2, linearDelay.calculate(2));
        assertEquals(2, linearDelay.calculate(3));
        assertEquals(2, linearDelay.calculate(4));
    }

    @Test
    public void shouldApplyFactor() {
        Delay linearDelay = new LinearDelay(1, TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 0.5);

        assertEquals(1, linearDelay.calculate(1));
        assertEquals(1, linearDelay.calculate(2));
        assertEquals(2, linearDelay.calculate(3));
        assertEquals(2, linearDelay.calculate(4));
        assertEquals(3, linearDelay.calculate(5));
        assertEquals(3, linearDelay.calculate(6));
    }

}
