    /**
     * Calculates the states for a {@link CouchbaseNode} based on the given {@link Service} states.
     *
     * The rules are as follows in strict order:
     *   1) No Service States -> Disconnected
     *   2) All Services Connected -> Connected
     *   3) At least one Service Connected -> Degraded
     *   4) At least one Service Connecting -> Connecting
     *   5) At least one Service Disconnecting -> Disconnecting
     *   6) Otherwise -> Disconnected
     *
     * @return the output node states.
     */
    private LifecycleState recalculateState() {
        if (serviceStates.isEmpty()) {
            return LifecycleState.DISCONNECTED;
        }
        int connected = 0;
        int connecting = 0;
        int disconnecting = 0;
        int idle = 0;
        for (LifecycleState serviceState : serviceStates.values()) {
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
        if (serviceStates.size() == idle) {
            return LifecycleState.IDLE;
        } else if (serviceStates.size() == (connected + idle)) {
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

