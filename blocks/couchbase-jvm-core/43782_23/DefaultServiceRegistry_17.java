
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import rx.Observable;

import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class StreamRequestResponse extends AbstractDCPResponse {
    private final Observable<DCPRequest> stream;
    private final List<FailoverLogEntry> failoverLog;

    public StreamRequestResponse(ResponseStatus status, Observable<DCPRequest> stream,
                                 List<FailoverLogEntry> failoverLog, CouchbaseRequest request) {
        super(status, request);
        this.stream = stream;
        this.failoverLog = failoverLog;
    }

    public Observable<DCPRequest> stream() {
        return stream;
    }

    public List<FailoverLogEntry> failoverLog() {
        return failoverLog;
    }
}
