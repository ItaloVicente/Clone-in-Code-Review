package com.couchbase.client.java.cluster.api;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpMethod;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class AsyncClusterApiClient extends AbstractClusterApiClient<AsyncRestBuilder> {

    public AsyncClusterApiClient(String username, String password, ClusterFacade core) {
        super(username, password, core);
    }

    @Override
    protected AsyncRestBuilder createBuilder(HttpMethod method, String fullPath) {
        return new AsyncRestBuilder(this.username, this.password, this.core, method, fullPath);
    }
}
