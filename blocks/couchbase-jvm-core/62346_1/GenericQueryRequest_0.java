
package com.couchbase.client.core.message;

import com.couchbase.client.core.config.NodeInfo;

public interface CouchbasePrelocatedRequest extends CouchbaseRequest {

    NodeInfo sendTo();


}
