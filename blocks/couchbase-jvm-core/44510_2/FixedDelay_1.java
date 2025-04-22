
package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public class ExponentialDelay extends Delay {

    private final long lower;
    private final long upper;

    public ExponentialDelay(TimeUnit unit, long upper, long lower) {
        super(unit);
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public long calculate(long attempt) {
        long calc = (long) Math.ceil(Math.exp(attempt));
        if (calc < lower) {
            return lower;
        }
        if (calc > upper) {
            return upper;
        }
        return calc;
    }
}
