
package com.couchbase.client.core.service.strategies;

import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.node.Node;
import com.couchbase.client.core.state.LifecycleState;

public class RoundRobinSelectionStrategy implements SelectionStrategy {

    protected volatile int skip = 0;

    @Override
    public Endpoint select(CouchbaseRequest request, Endpoint[] endpoints) {
        int endpointSize = endpoints.length;
        skip = Math.max(0, skip+1);
        int offset = skip % endpointSize;

        for (int i = offset; i < endpointSize; i++) {
            Endpoint endpoint = endpoints[i];
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }

        for (int i = 0; i < offset; i++) {
            Endpoint endpoint = endpoints[i];
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }

        return null;
    }

    protected void setSkip(int newValue) {
        if (newValue < 0)
            skip = 0;
        else
            skip = newValue;
    }
}
