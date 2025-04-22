
package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public abstract class Delay {

    private final TimeUnit unit;

    Delay(TimeUnit unit) {
        this.unit = unit;
    }

    public TimeUnit unit() {
        return unit;
    }

    public abstract long calculate(long attempt);

    public static Delay fixed(long time, TimeUnit unit) {
        return new FixedDelay(time, unit);
    }

    public static Delay linear(long time, TimeUnit unit) {
        return linear(time, unit, Long.MAX_VALUE);
    }

    public static Delay linear(long time, TimeUnit unit, long upper) {
        return linear(time, unit, upper, 0);
    }

    public static Delay linear(long time, TimeUnit unit, long upper, long lower) {
        return linear(time, unit, upper, lower, 1);
    }

    public static Delay linear(long time, TimeUnit unit, long upper, long lower, long factor) {
        return new LinearDelay(time, unit, upper, lower, factor);
    }

    public static Delay exponential(TimeUnit unit) {
        return exponential(unit, Long.MAX_VALUE);
    }

    public static Delay exponential(TimeUnit unit, long upper) {
        return exponential(unit, upper, 0);
    }

    public static Delay exponential(TimeUnit unit, long upper, long lower) {
        return new ExponentialDelay(unit, upper, lower);
    }

}
