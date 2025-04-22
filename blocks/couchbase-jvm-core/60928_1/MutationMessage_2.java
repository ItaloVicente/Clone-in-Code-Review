
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class ExpirationMessage extends AbstractDCPMessage {
    private final long cas;
    private final long bySequenceNumber;
    private final long revisionSequenceNumber;

    public ExpirationMessage(short partition, String key, long cas, long bySequenceNumber, long revisionSequenceNumber, String bucket) {
        this(partition, key, cas, bySequenceNumber, revisionSequenceNumber, bucket, null);
    }

    public ExpirationMessage(short partition, String key, long cas, long bySequenceNumber, long revisionSequenceNumber, String bucket, String password) {
        super(partition, key, bucket, password);
        this.cas = cas;
        this.bySequenceNumber = bySequenceNumber;
        this.revisionSequenceNumber = revisionSequenceNumber;
    }

    public long cas() {
        return cas;
    }

    public long bySequenceNumber() {
        return bySequenceNumber;
    }

    public long revisionSequenceNumber() {
        return revisionSequenceNumber;
    }
}
