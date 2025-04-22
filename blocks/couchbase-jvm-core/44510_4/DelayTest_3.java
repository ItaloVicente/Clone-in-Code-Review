package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public class LinearDelay extends Delay {

    private final double factor;
    private final long lower;
    private final long upper;

    LinearDelay(TimeUnit unit, long upper, long lower, double factor) {
        super(unit);

        this.factor = factor;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public long calculate(long attempt) {
        long calc = Math.round(attempt * factor);
        if (calc < lower) {
            return lower;
        }
        if (calc > upper) {
            return upper;
        }
        return calc;
    }
}
