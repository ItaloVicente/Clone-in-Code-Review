
package com.couchbase.client.core.message.dcp;

public class GetLastCheckpointRequest extends AbstractDCPRequest {
    public GetLastCheckpointRequest(final short partition, final String bucket) {
        super(bucket, null);
        partition(partition);
    }

    public GetLastCheckpointRequest(final short partition, final String bucket, final String password) {
        super(bucket, password);
        partition(partition);
    }
}
