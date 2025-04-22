
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.dcp.DCPConnection;
import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.Subject;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public abstract class AbstractDCPMessage extends AbstractDCPRequest implements DCPMessage {
    private final String key;
    private final int totalBodyLength;
    private final DCPConnection connection;

    public AbstractDCPMessage(DCPConnection connection, int totalBodyLength, short partition, String key, final String bucket, final String password) {
        super(bucket, password);
        this.partition(partition);
        this.key = key;
        this.totalBodyLength = totalBodyLength;
        this.connection = connection;
    }

    public AbstractDCPMessage(DCPConnection connection, int totalBodyLength, short partition, String key, final String bucket, final String password,
                              final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        super(bucket, password, observable);
        this.partition(partition);
        this.key = key;
        this.totalBodyLength = totalBodyLength;
        this.connection = connection;
    }

    @Override
    public int totalBodyLength() {
        return totalBodyLength;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public DCPConnection connection() {
        return connection;
    }
}
