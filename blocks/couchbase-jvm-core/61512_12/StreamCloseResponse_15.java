
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class StreamCloseRequest extends AbstractDCPRequest {
    public StreamCloseRequest(short partition, String bucket, String password) {
        super(bucket, password);
        this.partition(partition);
    }
}
