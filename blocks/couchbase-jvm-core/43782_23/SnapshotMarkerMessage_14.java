
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class RemoveMessage extends AbstractDCPRequest {
    private final String key;
    private final long cas;

    public RemoveMessage(short partition, String key, long cas, String bucket) {
        this(partition, key, cas, bucket, null);
    }

    public RemoveMessage(short partition, String key, long cas, String bucket, String password) {
        super(bucket, password);
        this.partition(partition);
        this.key = key;
        this.cas = cas;
    }

    public String key() {
        return key;
    }

    public long cas() {
        return cas;
    }
}
