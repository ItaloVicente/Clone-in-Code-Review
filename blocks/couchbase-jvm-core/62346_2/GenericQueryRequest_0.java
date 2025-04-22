
package com.couchbase.client.core.message;

import com.couchbase.client.core.config.NodeInfo;

import java.net.InetAddress;

public interface CouchbasePrelocatedRequest extends CouchbaseRequest {

    InetAddress sendTo();


}
