
package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;
import rx.Observable;

public class AnalyticsQueryResultResponse extends AbstractCouchbaseResponse {

    private final Observable<ByteBuf> rows;

    public AnalyticsQueryResultResponse(Observable<ByteBuf> rows, CouchbaseRequest request, ResponseStatus status) {
        super(status, request);
        this.rows = rows;
    }

    public Observable<ByteBuf> rows() {
        return this.rows;
    }
}
