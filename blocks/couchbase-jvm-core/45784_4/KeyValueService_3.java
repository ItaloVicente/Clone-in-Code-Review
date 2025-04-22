
package com.couchbase.client.core.service;

import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.state.AbstractStateZipper;
import com.couchbase.client.core.state.LifecycleState;

import java.util.Collection;

public class EndpointStateZipper extends AbstractStateZipper<Endpoint, LifecycleState> {

    public EndpointStateZipper() {
        super(LifecycleState.DISCONNECTED);
    }

    @Override
    protected LifecycleState zipWith(Collection<LifecycleState> states) {
        if (states.isEmpty()) {
            return LifecycleState.DISCONNECTED;
        }
        int connected = 0;
        int connecting = 0;
        int disconnecting = 0;
        for (LifecycleState endpointState : states) {
            switch (endpointState) {
                case CONNECTED:
                    connected++;
                    break;
                case CONNECTING:
                    connecting++;
                    break;
                case DISCONNECTING:
                    disconnecting++;
                    break;
                default:
            }
        }
        if (states.size() == connected) {
            return LifecycleState.CONNECTED;
        } else if (connected > 0) {
            return LifecycleState.DEGRADED;
        } else if (connecting > 0) {
            return LifecycleState.CONNECTING;
        } else if (disconnecting > 0) {
            return LifecycleState.DISCONNECTING;
        } else {
            return LifecycleState.DISCONNECTED;
        }
    }
}
