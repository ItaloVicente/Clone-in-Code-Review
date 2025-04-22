    @Override
    public Single<EndpointHealth> healthCheck(ServiceType type) {
        LifecycleState currentState = state();
        SocketAddress remoteAddr = null;
        SocketAddress localAddr = null;
        if(channel != null) {
            remoteAddr = channel.remoteAddress();
            localAddr = channel.localAddress();
        }
        long lastActivity = TimeUnit.NANOSECONDS.toMicros(lastResponse > 0 ? System.nanoTime() - lastResponse : 0);
        return Single.just(new EndpointHealth(type, currentState, localAddr, remoteAddr, lastActivity));
    }

