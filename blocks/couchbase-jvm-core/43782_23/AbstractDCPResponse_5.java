
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.Subject;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public abstract class AbstractDCPRequest extends AbstractCouchbaseRequest implements DCPRequest {
    protected static short DEFAULT_PARTITION = 0;

    private short partition = DEFAULT_PARTITION;

    public AbstractDCPRequest(String bucket, String password) {
        super(bucket, password);
    }

    public AbstractDCPRequest(String bucket, String password, Subject<CouchbaseResponse,
            CouchbaseResponse> observable) {
        super(bucket, password, observable);
    }

    @Override
    public short partition() {
        if (partition == -1) {
            throw new IllegalStateException("Partition requested but not set beforehand");
        }
        return partition;
    }

    @Override
    public DCPRequest partition(short partition) {
        if (partition < 0) {
            throw new IllegalArgumentException("Partition must be larger than or equal to zero");
        }
        this.partition = partition;
        return this;
    }
}
