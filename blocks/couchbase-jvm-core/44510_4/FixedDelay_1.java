package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public class ExponentialDelay extends Delay {

    private final long lower;
    private final long upper;
    private final double factor;

    ExponentialDelay(TimeUnit unit, long upper, long lower, double factor) {
        super(unit);
        this.lower = lower;
        this.upper = upper;
        this.factor = factor;
    }

    @Override
    public long calculate(long attempt) {
        long calc = Math.round(Math.exp(attempt) * factor);
        if (calc < lower) {
            return lower;
        }
        if (calc > upper) {
            return upper;
        }
        return calc;
    }
}
