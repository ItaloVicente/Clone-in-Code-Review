    @Override
    public Observable<ClusterInfo> info() {
        return
            ensureServiceEnabled()
            .flatMap(new Func1<Boolean, Observable<ClusterConfigResponse>>() {
                @Override
                public Observable<ClusterConfigResponse> call(Boolean aBoolean) {
                    return core.send(new ClusterConfigRequest(username, password));
                }
            })
            .map(new Func1<ClusterConfigResponse, ClusterInfo>() {
                @Override
                public ClusterInfo call(ClusterConfigResponse response) {
                    try {
                        return new DefaultClusterInfo(CouchbaseBucket.JSON_TRANSCODER.stringToJsonObject(response.config()));
                    } catch (Exception e) {
                        throw new CouchbaseException("Could not decode cluster info.", e);
                    }
                }
            });
    }

