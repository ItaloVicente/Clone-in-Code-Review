package com.couchbase.client.core.retry;

import com.couchbase.client.core.message.CouchbaseRequest;

public class BestEffortRetryStrategy implements RetryStrategy {

    public static final BestEffortRetryStrategy INSTANCE = new BestEffortRetryStrategy();

    private BestEffortRetryStrategy() {
    }

    @Override
    public boolean shouldRetry(final CouchbaseRequest request) {
        return true;
    }

    @Override
    public String toString() {
        return "BestEffort";
    }
}
