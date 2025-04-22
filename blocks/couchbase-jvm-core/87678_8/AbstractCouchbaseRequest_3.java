
        if (span != null) {
            span.setTag("peer.hostname", hostname);
        }
    }

    @Override
    public int dispatchPort() {
        return dispatchPort;
    }

    @Override
    public void dispatchPort(int port) {
        this.dispatchPort = port;

        if (span != null) {
            span.setTag("peer.port", dispatchPort);
        }
