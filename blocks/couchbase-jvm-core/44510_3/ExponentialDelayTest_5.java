
package com.couchbase.client.core.time;

import org.junit.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ExponentialDelayTest {

    @Test
    public void shouldCalculateExponentially() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 1);

        assertEquals(3, exponentialDelay.calculate(1));
        assertEquals(7, exponentialDelay.calculate(2));
        assertEquals(20, exponentialDelay.calculate(3));
        assertEquals(55, exponentialDelay.calculate(4));
        assertEquals(148, exponentialDelay.calculate(5));
        assertEquals(403, exponentialDelay.calculate(6));
    }

    @Test
    public void shouldRespectLowerBound() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 50, 1);

        assertEquals(50, exponentialDelay.calculate(1));
        assertEquals(50, exponentialDelay.calculate(2));
        assertEquals(50, exponentialDelay.calculate(3));
        assertEquals(55, exponentialDelay.calculate(4));
        assertEquals(148, exponentialDelay.calculate(5));
        assertEquals(403, exponentialDelay.calculate(6));
    }

    @Test
    public void shouldRespectUpperBound() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, 70, 0, 1);

        assertEquals(3, exponentialDelay.calculate(1));
        assertEquals(7, exponentialDelay.calculate(2));
        assertEquals(20, exponentialDelay.calculate(3));
        assertEquals(55, exponentialDelay.calculate(4));
        assertEquals(70, exponentialDelay.calculate(5));
        assertEquals(70, exponentialDelay.calculate(6));
    }

    @Test
    public void shouldApplyFactor() {
        Delay exponentialDelay = new ExponentialDelay(TimeUnit.SECONDS, Integer.MAX_VALUE, 0, 0.5);

        assertEquals(1, exponentialDelay.calculate(1));
        assertEquals(4, exponentialDelay.calculate(2));
        assertEquals(10, exponentialDelay.calculate(3));
        assertEquals(27, exponentialDelay.calculate(4));
        assertEquals(74, exponentialDelay.calculate(5));
        assertEquals(202, exponentialDelay.calculate(6));
    }

}
