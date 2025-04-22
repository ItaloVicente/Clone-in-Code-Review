
package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import rx.Observable;

public class AnalyticsQueryStatusResponse extends AbstractCouchbaseResponse {

    private final Observable<String> queryStatus;
    private final String resultHandle;

    public AnalyticsQueryStatusResponse(Observable<String> queryStatus, String resultHandle, CouchbaseRequest request, ResponseStatus status) {
        super(status, request);
        this.queryStatus = queryStatus;
        this.resultHandle = resultHandle;
    }

    public Observable<String> queryStatus() {
        return this.queryStatus;
    }

    public String resultHandle() {
        return this.resultHandle;
    }
}
