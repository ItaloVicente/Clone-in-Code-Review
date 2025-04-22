        return core
                .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
                .flatMap(new Func1<GetClusterConfigResponse, Observable<NodeInfo>>() {
                    @Override
                    public Observable<NodeInfo> call(GetClusterConfigResponse response) {
                        CouchbaseBucketConfig cfg = (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                        return Observable.from(cfg.nodes());
                    }
                })
                .flatMap(new Func1<NodeInfo, Observable<GetAllMutationTokensResponse>>() {
                    @Override
                    public Observable<GetAllMutationTokensResponse> call(NodeInfo node) {
                        return core.send(new GetAllMutationTokensRequest(node.hostname(), bucket));
                    }
                })
                .collect(new Func0<Map<Integer, MutationToken>>() {
                    @Override
                    public Map<Integer, MutationToken> call() {
                        return new HashMap<Integer, MutationToken>(1024);
                    }
                }, new Action2<Map<Integer, MutationToken>, GetAllMutationTokensResponse>() {
