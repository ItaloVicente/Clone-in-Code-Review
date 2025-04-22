
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public enum ConnectionType {
    CONSUMER(0),
    PRODUCER(1);

    private final int flags;

    private ConnectionType(int flags) {
        this.flags = flags;
    }

    public int flags() {
        return flags;
    }
}
