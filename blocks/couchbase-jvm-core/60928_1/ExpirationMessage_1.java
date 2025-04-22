
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.dcp.DCPConnection;
import com.couchbase.client.core.message.CouchbaseRequest;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public interface DCPMessage {
    String key();
}
