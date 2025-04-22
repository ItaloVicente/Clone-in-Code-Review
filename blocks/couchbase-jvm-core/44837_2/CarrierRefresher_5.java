    private Observable<String> refreshAgainstNode(final String bucketName, final InetAddress hostname) {
        return cluster()
            .<GetBucketConfigResponse>send(new GetBucketConfigRequest(bucketName, hostname))
            .filter(new Func1<GetBucketConfigResponse, Boolean>() {
                @Override
                public Boolean call(GetBucketConfigResponse response) {
                    boolean good = response.status().isSuccess() && response.content() != null;
                    if (!good) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }
                    }
                    return good;
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
                public void call(Throwable throwable) {
                    LOGGER.debug("Could not fetch config from bucket \"{}\" against {}.", bucketName, hostname);
