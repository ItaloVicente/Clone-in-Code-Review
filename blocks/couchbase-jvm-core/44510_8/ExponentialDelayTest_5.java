package com.couchbase.client.core.time;

import org.junit.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ExponentialDelayTest {

    @Test
    public void shouldCalculateExponentially() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 1);

        assertEquals(1, exponentialDelay.calculate(1));
        assertEquals(2, exponentialDelay.calculate(2));
        assertEquals(4, exponentialDelay.calculate(3));
        assertEquals(8, exponentialDelay.calculate(4));
        assertEquals(16, exponentialDelay.calculate(5));
        assertEquals(32, exponentialDelay.calculate(6));
    }

    @Test
    public void shouldRespectLowerBound() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 10, 1);

        assertEquals(10, exponentialDelay.calculate(1));
        assertEquals(10, exponentialDelay.calculate(2));
        assertEquals(10, exponentialDelay.calculate(3));
        assertEquals(10, exponentialDelay.calculate(4));
        assertEquals(16, exponentialDelay.calculate(5));
        assertEquals(32, exponentialDelay.calculate(6));
    }

    @Test
    public void shouldRespectUpperBound() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, 9, 0, 1);

        assertEquals(1, exponentialDelay.calculate(1));
        assertEquals(2, exponentialDelay.calculate(2));
        assertEquals(4, exponentialDelay.calculate(3));
        assertEquals(8, exponentialDelay.calculate(4));
        assertEquals(9, exponentialDelay.calculate(5));
        assertEquals(9, exponentialDelay.calculate(6));
    }

    @Test
    public void shouldApplyFactor() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 2);

        assertEquals(2, exponentialDelay.calculate(1));
        assertEquals(4, exponentialDelay.calculate(2));
        assertEquals(8, exponentialDelay.calculate(3));
        assertEquals(16, exponentialDelay.calculate(4));
        assertEquals(32, exponentialDelay.calculate(5));
        assertEquals(64, exponentialDelay.calculate(6));
    }

}
