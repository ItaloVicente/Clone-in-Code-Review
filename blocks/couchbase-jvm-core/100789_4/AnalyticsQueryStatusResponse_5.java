
package com.couchbase.client.core.message.analytics;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.PrelocatedRequest;
import java.net.InetAddress;

public class AnalyticsQueryStatusRequest extends AbstractCouchbaseRequest implements AnalyticsRequest, PrelocatedRequest {

    private final InetAddress target;
    private final String statusPath;

    public AnalyticsQueryStatusRequest(String statusPath, String bucket, String username, String password) {
        this(statusPath, bucket, username, password, null);
    }

    public AnalyticsQueryStatusRequest(String statusPath, String bucket, String username, String password, InetAddress target) {
        super(bucket, username, password);
        this.statusPath = statusPath;
        this.target = target;
    }

    @Override
    public String path() {
        return statusPath;
    }

    @Override
    public InetAddress sendTo() {
        return target;
    }
}
