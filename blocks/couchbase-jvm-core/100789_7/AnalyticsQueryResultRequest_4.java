
package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.PrelocatedRequest;

import java.net.InetAddress;

@InterfaceStability.Experimental
public class AnalyticsQueryResultRequest extends AbstractCouchbaseRequest implements AnalyticsRequest, PrelocatedRequest {

    private final InetAddress target;
    private final String resultPath;

    public AnalyticsQueryResultRequest(String uri, String bucket, String username, String password) {
        this(uri, bucket, username, password, null);
    }

    public AnalyticsQueryResultRequest(String uri, String bucket, String username, String password, InetAddress target) {
        super(bucket, username, password);
        String requestIdentifier = uri.substring(uri.lastIndexOf('/') + 1);
        this.resultPath = "/analytics/service/result/" + requestIdentifier;
        this.target = target;
    }

    @Override
    public String path() {
        return resultPath;
    }

    @Override
    public InetAddress sendTo() {
        return target;
    }
}
