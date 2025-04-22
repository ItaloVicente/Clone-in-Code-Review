
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public abstract class AbstractDCPResponse extends AbstractCouchbaseResponse implements DCPResponse {
    public AbstractDCPResponse(ResponseStatus status, CouchbaseRequest request) {
        super(status, request);
    }
}
