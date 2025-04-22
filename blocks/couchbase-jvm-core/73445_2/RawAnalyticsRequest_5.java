package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.utils.Buffers;
import io.netty.buffer.ByteBuf;
import rx.Observable;

public class GenericAnalyticsResponse extends AbstractCouchbaseResponse {

    private final Observable<ByteBuf> errors;
    private final Observable<ByteBuf> rows;
    private final Observable<String> queryStatus;
    private final Observable<ByteBuf> info;
    private final Observable<ByteBuf> signature;

    private final String requestId;
    private final String clientRequestId;

    public GenericAnalyticsResponse(Observable<ByteBuf> errors, Observable<ByteBuf> rows, Observable<ByteBuf> signature,
                                    Observable<String> queryStatus, Observable<ByteBuf> info,
                                    CouchbaseRequest request, ResponseStatus status, String requestId, String clientRequestId) {
        super(status, request);
        this.errors = errors;
        this.rows = rows;
        this.signature = signature;
        this.info = info;
        this.queryStatus = queryStatus;
        this.requestId = requestId;
        this.clientRequestId = clientRequestId == null ? "" : clientRequestId;
    }

    public Observable<ByteBuf> rows() {
        return rows;
    }

    public Observable<ByteBuf> signature() {
        return this.signature;
    }

    public Observable<ByteBuf> errors() {
        return errors;
    }

    public Observable<String> queryStatus() {
        return queryStatus;
    }

    public Observable<ByteBuf> info() { return info; }

    public String requestId() {
        return requestId;
    }

    public String clientRequestId() {
        return clientRequestId;
    }
}
