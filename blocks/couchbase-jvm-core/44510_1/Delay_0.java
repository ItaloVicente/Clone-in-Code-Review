
package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public abstract class AbstractDelay implements Delay {

    private final TimeUnit unit;

    AbstractDelay(TimeUnit unit) {
        this.unit = unit;
    }

    @Override
    public TimeUnit unit() {
        return unit;
    }

}
