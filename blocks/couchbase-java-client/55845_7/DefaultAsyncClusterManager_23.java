        int port = environment.sslEnabled()
                ? environment.bootstrapHttpSslPort() : environment.bootstrapHttpDirectPort();
        return ensureServiceEnabled(ServiceType.CONFIG, port);
    }

    private Observable<Boolean> ensureServiceEnabled(final ServiceType service, final int port) {
