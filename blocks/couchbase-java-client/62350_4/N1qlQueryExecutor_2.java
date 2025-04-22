        Observable<GenericQueryResponse> source;


        if (isBandwidthOptimizationOn()) {
            source = Observable.defer(new Func0<Observable<GetClusterConfigResponse>>() {
                @Override
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
                    GenericQueryRequest req = createN1qlRequest(query, bucket, password, nodeInfo.hostname());
                    return core.send(req);
                }
            });
        } else {
            source = Observable.defer(new Func0<Observable<GenericQueryResponse>>() {
                @Override
                public Observable<GenericQueryResponse> call() {
                    return core.send(createN1qlRequest(query, bucket, password, null));
                }
            });
        }

        return source.flatMap(new Func1<GenericQueryResponse, Observable<PreparedPayload>>() {
