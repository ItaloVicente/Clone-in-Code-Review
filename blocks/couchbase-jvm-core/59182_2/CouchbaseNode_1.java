    private void signalConnected() {
        LOGGER.info("Connected to Node " + hostname.getHostName());
        if (eventBus != null && eventBus.hasSubscribers()) {
            eventBus.publish(new NodeConnectedEvent(hostname));
        }
    }

    private void signalDisconnected() {
        LOGGER.info("Disconnected from Node " + hostname.getHostName());
        if (eventBus != null && eventBus.hasSubscribers()) {
            eventBus.publish(new NodeDisconnectedEvent(hostname));
        }
    }

