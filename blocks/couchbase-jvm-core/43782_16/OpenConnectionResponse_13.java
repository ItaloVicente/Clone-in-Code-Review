
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class OpenConnectionRequest extends AbstractDCPRequest {
    private final ConnectionType type;

    private final String connectionName;
    private final int sequenceNumber;

    public OpenConnectionRequest(String connectionName, String bucket) {
        this(connectionName, ConnectionType.PRODUCER, 0, bucket, null);
    }

    public OpenConnectionRequest(String connectionName, String bucket, String password) {
        this(connectionName, ConnectionType.PRODUCER, 0, bucket, password);
    }

    public OpenConnectionRequest(String connectionName, ConnectionType type, String bucket) {
        this(connectionName, type, 0, bucket, null);
    }

    public OpenConnectionRequest(String connectionName, ConnectionType type, String bucket, String password) {
        this(connectionName, type, 0, bucket, password);
    }

    public OpenConnectionRequest(String connectionName, ConnectionType type, int sequenceNumber, String bucket, String password) {
        super(bucket, password);
        this.type = type;
        this.sequenceNumber = sequenceNumber;
        this.connectionName = connectionName;
    }

    public int sequenceNumber() {
        return sequenceNumber;
    }

    public String connectionName() {
        return connectionName;
    }

    public ConnectionType type() {
        return type;
    }

}
