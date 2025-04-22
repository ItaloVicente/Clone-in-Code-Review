            public Observable<GetClusterConfigResponse> call() {
                return core.send(new GetClusterConfigRequest());
            }
        }).flatMap(new Func1<GetClusterConfigResponse, Observable<NodeInfo>>() {
            @Override
            public Observable<NodeInfo> call(GetClusterConfigResponse getClusterConfigResponse) {
                return Observable.from(getClusterConfigResponse.config()
                        .bucketConfig(bucket)
                        .nodes());
            }
        }).flatMap(new Func1<NodeInfo, Observable<GenericQueryResponse>>() {
            @Override
            public Observable<GenericQueryResponse> call(NodeInfo nodeInfo) {
                GenericQueryRequest req = createN1qlRequest(query, bucket, password);
                req.sendTo(nodeInfo.hostname());
                return core.send(req);
