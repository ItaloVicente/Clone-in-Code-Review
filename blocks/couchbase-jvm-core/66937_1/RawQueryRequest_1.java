
package com.couchbase.client.core.message.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.PrelocatedRequest;

import java.net.InetAddress;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class RawQueryRequest extends AbstractCouchbaseRequest implements QueryRequest, PrelocatedRequest {

    private final String query;
    private final InetAddress targetNode;

    private RawQueryRequest(String jsonQuery, String bucket, String password, InetAddress targetNode) {
        super(bucket, password);
        this.query = jsonQuery;
        this.targetNode = targetNode;
    }

    public String query() {
        return query;
    }

    @Override
    public InetAddress sendTo() {
        return targetNode;
    }

    public static RawQueryRequest jsonQuery(String jsonQuery, String bucket, String password) {
        return new RawQueryRequest(jsonQuery, bucket, password, null);
    }

    public static RawQueryRequest jsonQuery(String jsonQuery, String bucket, String password, InetAddress targetNode) {
        return new RawQueryRequest(jsonQuery, bucket, password, targetNode);
    }
}
