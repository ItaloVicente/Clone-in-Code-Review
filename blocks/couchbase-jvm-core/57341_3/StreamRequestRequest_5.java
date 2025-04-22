
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

import java.util.List;

public class GetFailoverLogResponse extends AbstractDCPResponse {
    private final List<FailoverLogEntry> failoverLog;

    public GetFailoverLogResponse(final ResponseStatus status, final List<FailoverLogEntry> failoverLog,
                                  final CouchbaseRequest request) {
        super(status, request);
        this.failoverLog = failoverLog;
    }

    public List<FailoverLogEntry> failoverLog() {
        return failoverLog;
    }
}
