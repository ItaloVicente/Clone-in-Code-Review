package com.couchbase.client.core.time;

import org.junit.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DelayTest {

    @Test
    public void shouldBuildFixedDelay() {
        Delay delay = Delay.fixed(5, TimeUnit.MICROSECONDS);
        assertEquals(TimeUnit.MICROSECONDS, delay.unit());
        assertEquals(5, delay.calculate(10));
    }

    @Test
    public void shouldBuildLinearDelay() {
        Delay delay = Delay.linear(TimeUnit.HOURS);
        assertEquals(TimeUnit.HOURS, delay.unit());
        assertEquals(10, delay.calculate(10));
    }

    @Test
    public void shouldBuildExponentialDelay() {
        Delay delay = Delay.exponential(TimeUnit.SECONDS);
        assertEquals(TimeUnit.SECONDS, delay.unit());
        assertEquals(22026, delay.calculate(10));
    }

}
