package com.couchbase.client.core.retry;

import com.couchbase.client.core.message.CouchbaseRequest;

public interface RetryStrategy {

    boolean shouldRetry(CouchbaseRequest request);

}
