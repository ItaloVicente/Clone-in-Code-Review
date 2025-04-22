
package com.couchbase.client.core.tracing;

public class SlowlogReporter {

    public void report(final SlowlogSpan span) {
        System.err.println(span);
    }

}
