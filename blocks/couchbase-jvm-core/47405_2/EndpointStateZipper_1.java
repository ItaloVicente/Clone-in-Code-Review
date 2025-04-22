package com.couchbase.client.core.node;

import com.couchbase.client.core.service.Service;
import com.couchbase.client.core.state.AbstractStateZipper;
import com.couchbase.client.core.state.LifecycleState;

import java.util.Collection;

public class ServiceStateZipper extends AbstractStateZipper<Service, LifecycleState>  {

    private final LifecycleState initialState;

    public ServiceStateZipper(LifecycleState initial) {
        super(initial);
        this.initialState = initial;
    }

    @Override
    protected LifecycleState zipWith(Collection<LifecycleState> states) {
        if (states.isEmpty()) {
            return LifecycleState.DISCONNECTED;
        }

        int connected = 0;
        int connecting = 0;
        int disconnecting = 0;
        int idle = 0;
        for (LifecycleState serviceState : states) {
            switch (serviceState) {
                case CONNECTED:
                    connected++;
                    break;
                case CONNECTING:
                    connecting++;
                    break;
                case DISCONNECTING:
                    disconnecting++;
                    break;
                case IDLE:
                    idle++;
                    break;
            }
        }
        if (states.size() == idle) {
            return LifecycleState.IDLE;
        } else if (states.size() == (connected + idle)) {
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
