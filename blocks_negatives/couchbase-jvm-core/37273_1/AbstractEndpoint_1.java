    public void notifyChannelAuthSuccess() {
        transitionState(LifecycleState.CONNECTED);
        LOGGER.debug("Successfully authenticated to Endpoint " + channel.remoteAddress());
    }

    public void notifyChannelAuthFailure() {
        transitionState(LifecycleState.DISCONNECTED);
        throw new RuntimeException("Auth failure");
    }

