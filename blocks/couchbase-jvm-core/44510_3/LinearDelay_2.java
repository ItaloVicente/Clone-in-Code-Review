
package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public class FixedDelay extends Delay {

    private final long time;

    public FixedDelay(long time, TimeUnit unit) {
        super(unit);
        this.time = time;
    }

    public long time() {
        return time;
    }

    @Override
    public long calculate(long attempt) {
        return time();
    }

}
