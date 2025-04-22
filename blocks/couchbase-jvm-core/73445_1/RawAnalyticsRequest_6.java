
package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.PrelocatedRequest;
import com.couchbase.client.core.message.query.QueryRequest;

import java.net.InetAddress;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class RawAnalyticsRequest extends GenericAnalyticsRequest {

    private RawAnalyticsRequest(String jsonQuery, String bucket, String password, InetAddress targetNode) {
        super(jsonQuery, true, bucket, password, targetNode);
    }

    public static RawAnalyticsRequest jsonQuery(String jsonQuery, String bucket, String password) {
        return new RawAnalyticsRequest(jsonQuery, bucket, password, null);
    }

    public static RawAnalyticsRequest jsonQuery(String jsonQuery, String bucket, String password, InetAddress targetNode) {
        return new RawAnalyticsRequest(jsonQuery, bucket, password, targetNode);
    }
}
