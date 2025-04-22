    public void notifyChannelInactive() {
        transitionState(LifecycleState.DISCONNECTED);
        connect();
    }

    public void notifyChannelAuthSuccess() {
        transitionState(LifecycleState.CONNECTED);
        LOGGER.debug("Successfully authenticated to Endpoint " + channel.remoteAddress());
    }

    public void notifyChannelAuthFailure() {
