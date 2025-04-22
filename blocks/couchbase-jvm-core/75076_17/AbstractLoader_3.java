        return loadConfig(seedNode, bucket, bucket, password);
    }

    public Observable<Tuple2<LoaderType, BucketConfig>> loadConfig(final InetAddress seedNode, final String bucket,
                                                                   final String username, final String password) {
        LOGGER.debug("Loading Config for bucket {}", bucket);
        return loadConfigAtAddr(seedNode, bucket, username, password);
