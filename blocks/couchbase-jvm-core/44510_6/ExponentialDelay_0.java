package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public abstract class Delay {

    private final TimeUnit unit;

    Delay(TimeUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("TimeUnit is not allowed to be null");
        }

        this.unit = unit;
    }

    public TimeUnit unit() {
        return unit;
    }

    public abstract long calculate(long attempt);

    public static Delay fixed(long delay, TimeUnit unit) {
        return new FixedDelay(delay, unit);
    }

    public static Delay linear(TimeUnit unit) {
        return linear(unit, Long.MAX_VALUE);
    }

    public static Delay linear(TimeUnit unit, long upper) {
        return linear(unit, upper, 0);
    }

    public static Delay linear(TimeUnit unit, long upper, long lower) {
        return linear(unit, upper, lower, 1);
    }

    public static Delay linear(TimeUnit unit, long upper, long lower, long growBy) {
        return new LinearDelay(unit, upper, lower, growBy);
    }

    public static Delay exponential(TimeUnit unit) {
        return exponential(unit, Long.MAX_VALUE);
    }

    public static Delay exponential(TimeUnit unit, long upper) {
        return exponential(unit, upper, 0);
    }

    public static Delay exponential(TimeUnit unit, long upper, long lower) {
        return exponential(unit, upper, lower, 1);
    }

    public static Delay exponential(TimeUnit unit, long upper, long lower, long growBy) {
        return new ExponentialDelay(unit, upper, lower, growBy);
    }

}
