
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.CouchbaseRequest;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public interface DCPRequest extends CouchbaseRequest {
    short partition();

    DCPRequest partition(short id);
}
