                                                final InetAddress hostname) {
        return discoverConfig(bucket, bucket, password, hostname);
    }

    @Override
    protected Observable<String> discoverConfig(final String bucket, final String username, final String password,
