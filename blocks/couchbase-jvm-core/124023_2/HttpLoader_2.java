    protected int port(String hostname) {
        Integer portFromConfig = tryLoadingPortFromConfig(hostname);
        if (portFromConfig != null) {
            return portFromConfig;
        }

        int port = env().sslEnabled() ? env().bootstrapHttpSslPort() : env().bootstrapHttpDirectPort();
        LOGGER.trace("Picked (default) port " + port + " for " + hostname);
        return port;
