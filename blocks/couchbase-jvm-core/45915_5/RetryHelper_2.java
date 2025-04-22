package com.couchbase.client.core.retry;

import com.couchbase.client.core.message.CouchbaseRequest;

public class FailFastRetryStrategy implements RetryStrategy {

    public static final FailFastRetryStrategy INSTANCE = new FailFastRetryStrategy();

    private FailFastRetryStrategy() {
    }

    @Override
    public boolean shouldRetry(CouchbaseRequest request) {
        return false;
    }

    @Override
    public String toString() {
        return "FailFast";
    }
}
