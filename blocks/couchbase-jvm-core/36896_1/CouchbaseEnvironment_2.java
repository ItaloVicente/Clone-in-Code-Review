    @Override
    public boolean enableSsl() {
        return getBoolean("core.bootstrap.enableSsl");
    }

    @Override
    public int bootstrapHttpDirectPort() {
        int port = getInt("core.bootstrap.http.directPort");
        if (port <= 0) {
            throw new EnvironmentException("Port must be greater than 0.");
        }
        return port;
    }

    @Override
    public int bootstrapHttpSslPort() {
        int port = getInt("core.bootstrap.http.sslPort");
        if (port <= 0) {
            throw new EnvironmentException("Port must be greater than 0.");
        }
        return port;
    }

    @Override
    public int bootstrapCarrierDirectPort() {
        int port = getInt("core.bootstrap.carrier.directPort");
        if (port <= 0) {
            throw new EnvironmentException("Port must be greater than 0.");
        }
        return port;
    }

    @Override
    public int bootstrapCarrierSslPort() {
        int port = getInt("core.bootstrap.carrier.sslPort");
        if (port <= 0) {
            throw new EnvironmentException("Port must be greater than 0.");
        }
        return port;
    }

