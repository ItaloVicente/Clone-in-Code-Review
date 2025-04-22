
package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public class LinearDelay extends Delay {

    private final long time;
    private final double factor;
    private final long lower;
    private final long upper;

    public LinearDelay(long time, TimeUnit unit, long upper, long lower, double factor) {
        super(unit);

        this.time = time;
        this.factor = factor;
        this.lower = lower;
        this.upper = upper;
    }

    public long time() {
        return time;
    }

    @Override
    public long calculate(long attempt) {
        long calc = Math.round(attempt * time() * factor);
        if (calc < lower) {
            return lower;
        }
        if (calc > upper) {
            return upper;
        }
        return calc;
    }
}
