
package com.couchbase.client.core.service.strategies;

import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.state.LifecycleState;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSelectionStrategy implements SelectionStrategy {
    protected static Endpoint selectFirstConnected(final Endpoint[] endpoints) {
        for (Endpoint endpoint : endpoints) {
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }
        return null;
    }

    protected static List<Endpoint> selectAllConnected(Endpoint[] endpoints) {
        List<Endpoint> selected = new ArrayList<Endpoint>();
        for (Endpoint endpoint : endpoints) {
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                selected.add(endpoint);
            }
        }
        return selected;
    }
}
