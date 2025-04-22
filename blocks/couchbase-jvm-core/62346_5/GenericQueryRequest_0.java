
package com.couchbase.client.core.message;

import com.couchbase.client.core.config.NodeInfo;

import java.net.InetAddress;

public interface PrelocatedRequest extends CouchbaseRequest {

    InetAddress sendTo();


}
