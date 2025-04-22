package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public class ExponentialDelay extends Delay {

    private final long lower;
    private final long upper;
    private final double growBy;

    ExponentialDelay(TimeUnit unit, long upper, long lower, double growBy) {
        super(unit);
        this.lower = lower;
        this.upper = upper;
        this.growBy = growBy;
    }

    @Override
    public long calculate(long attempt) {
        long calc = Math.round((1 << (attempt - 1)) * growBy);
        if (calc < lower) {
            return lower;
        }
        if (calc > upper) {
            return upper;
        }
        return calc;
    }
}
