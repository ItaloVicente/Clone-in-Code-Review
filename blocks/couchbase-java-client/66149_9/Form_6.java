package com.couchbase.client.java.cluster.api;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpMethod;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class ClusterApiClient extends AbstractClusterApiClient<RestBuilder> {


    private final long defaultTimeout;
    private final TimeUnit defaultTimeUnit;

    public ClusterApiClient(String username, String password, ClusterFacade core,
            long defaultTimeout, TimeUnit defaultTimeUnit) {
        super(username, password, core);
        this.defaultTimeout = defaultTimeout;
        this.defaultTimeUnit = defaultTimeUnit;
    }

    @Override
    protected RestBuilder createBuilder(HttpMethod method, String fullPath) {
        return new RestBuilder(new AsyncRestBuilder(this.username, this.password, this.core, method, fullPath),
                this.defaultTimeout, this.defaultTimeUnit);
    }

    public long defaultTimeout() {
        return this.defaultTimeout;
    }

    public TimeUnit defaultTimeUnit() {
        return this.defaultTimeUnit;
    }
}
