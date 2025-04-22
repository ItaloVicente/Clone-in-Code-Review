package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.PrelocatedRequest;
import com.couchbase.client.core.message.query.QueryRequest;

import java.net.InetAddress;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class GenericAnalyticsRequest extends AbstractCouchbaseRequest implements AnalyticsRequest, PrelocatedRequest {

    private final String query;
    private final boolean jsonFormat;
    private final InetAddress targetNode;

    protected GenericAnalyticsRequest(String query, boolean jsonFormat, String bucket, String password,
        InetAddress targetNode) {
        super(bucket, password);
        this.query = query;
        this.jsonFormat = jsonFormat;
        this.targetNode = targetNode;
    }

    public String query() {
        return query;
    }

    public boolean isJsonFormat() {
        return jsonFormat;
    }

    @Override
    public InetAddress sendTo() {
        return targetNode;
    }

    public static GenericAnalyticsRequest simpleStatement(String statement, String bucket, String password) {
        return new GenericAnalyticsRequest(statement, false, bucket, password, null);
    }

    public static GenericAnalyticsRequest jsonQuery(String jsonQuery, String bucket, String password) {
        return new GenericAnalyticsRequest(jsonQuery, true, bucket, password, null);
    }

    public static GenericAnalyticsRequest jsonQuery(String jsonQuery, String bucket, String password, InetAddress targetNode) {
        return new GenericAnalyticsRequest(jsonQuery, true, bucket, password, targetNode);
    }
}
