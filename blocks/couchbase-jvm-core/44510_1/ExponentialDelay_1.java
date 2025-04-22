
package com.couchbase.client.core.time;

import java.util.concurrent.TimeUnit;

public interface Delay {

    TimeUnit unit();

    long next(long attempt);

}
