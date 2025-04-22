        return cluster()
            .<GetBucketConfigResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new GetBucketConfigRequest(bucketName, hostname);
                }
            })
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
