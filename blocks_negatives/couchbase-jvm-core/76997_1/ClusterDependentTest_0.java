    private static final CoreEnvironment env = DefaultCoreEnvironment
            .builder()
            .dcpEnabled(true)
            .mutationTokensEnabled(true)
            .keepAliveInterval(KEEPALIVE_INTERVAL)
            .build();

