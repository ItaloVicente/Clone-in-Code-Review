    private Observable<String> refreshAgainstNode(final String bucketName, final InetAddress hostname) {
        return cluster()
            .<GetBucketConfigResponse>send(new GetBucketConfigRequest(bucketName, hostname))
            .doOnNext(new Action1<GetBucketConfigResponse>() {
                @Override
                public void call(GetBucketConfigResponse response) {
                    if (!response.status().isSuccess()) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }
                        throw new ConfigurationException("Could not fetch config from node: " + response);
                    }
                }
            })
            .map(new Func1<GetBucketConfigResponse, String>() {
                @Override
                public String call(GetBucketConfigResponse response) {
                    String raw = response.content().toString(CharsetUtil.UTF_8).trim();
                    if (response.content().refCnt() > 0) {
                        response.content().release();
                    }
                    return raw.replace("$HOST", response.hostname().getHostName());
                }
            })
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable ex) {
                    LOGGER.debug("Could not fetch config from bucket \"" + bucketName + "\" against \""
                        + hostname + "\".", ex);
