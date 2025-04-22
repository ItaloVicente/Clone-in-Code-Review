package com.couchbase.client.core.retry;

import com.couchbase.client.core.RequestCancelledException;
import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.ResponseHandler;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.lmax.disruptor.RingBuffer;

public class RetryHelper {

    public static void retryOrCancel(final RetryStrategy strategy, final CouchbaseRequest request,
        final RingBuffer<ResponseEvent> responseBuffer) {
        if (strategy.shouldRetry(request)) {
            responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable());
        } else {
            request.observable().onError(new RequestCancelledException("Could not dispatch request, cancelling "
                + "instead of retrying."));
        }
    }
}
