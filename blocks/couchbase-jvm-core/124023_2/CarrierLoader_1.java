    protected int port(final String hostname) {
        Integer portFromConfig = tryLoadingPortFromConfig(hostname);
        if (portFromConfig != null) {
            return portFromConfig;
        }

        int port = env().sslEnabled() ? env().bootstrapCarrierSslPort() : env().bootstrapCarrierDirectPort();
        LOGGER.trace("Picked (default) port " + port + " for " + hostname);
        return port;
