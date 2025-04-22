    private void signalServiceConnected(ServiceType serviceType) {
        if (eventBus != null && eventBus.hasSubscribers()) {
            eventBus.publish(new ServiceConnectedEvent(hostname, serviceType));
        }
    }

    private void signalServiceDisconnected(ServiceType serviceType) {
        if (eventBus != null && eventBus.hasSubscribers()) {
            eventBus.publish(new ServiceDisconnectedEvent(hostname, serviceType));
        }
    }

