
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class FailoverLogEntry {
    private final long vbucketUUID;
    private final long sequenceNumber;

    public FailoverLogEntry(final long vbucketUUID, final long sequenceNumber) {
        this.vbucketUUID = vbucketUUID;
        this.sequenceNumber = sequenceNumber;
    }

    public long sequenceNumber() {
        return sequenceNumber;
    }

    public long vbucketUUID() {
        return vbucketUUID;
    }
}
