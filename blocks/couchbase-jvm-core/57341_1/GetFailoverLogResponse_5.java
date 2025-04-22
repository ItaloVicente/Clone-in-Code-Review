
package com.couchbase.client.core.message.dcp;

public class GetFailoverLogRequest extends AbstractDCPRequest {
    public GetFailoverLogRequest(final short partition, final String bucket) {
        super(bucket, null);
        partition(partition);
    }

    public GetFailoverLogRequest(final short partition, final String bucket, final String password) {
        super(bucket, password);
        partition(partition);
    }
}
