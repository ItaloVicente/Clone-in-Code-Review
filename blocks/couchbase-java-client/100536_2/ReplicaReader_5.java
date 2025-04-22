
    private static Observable<BinaryRequest> assembleRequests(final long collectionId, final ClusterFacade core, final String id,
                                                              final ReplicaMode type, final String bucket) {
        if (type != ReplicaMode.ALL) {
            ReplicaGetRequest request = new ReplicaGetRequest(id, bucket, (short) type.ordinal());
            request.collectionId(collectionId);
            return Observable.just((BinaryRequest)request);
        }

        return Observable.defer(new Func0<Observable<GetClusterConfigResponse>>() {
            @Override
            public Observable<GetClusterConfigResponse> call() {
                return core.send(new GetClusterConfigRequest());
            }
        })
                .map(new Func1<GetClusterConfigResponse, Integer>() {
                    @Override
                    public Integer call(GetClusterConfigResponse response) {
                        CouchbaseBucketConfig conf = (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                        return conf.numberOfReplicas();
                    }
                })
                .flatMap(new Func1<Integer, Observable<BinaryRequest>>() {
                    @Override
                    public Observable<BinaryRequest> call(Integer max) {
                        List<BinaryRequest> requests = new ArrayList<BinaryRequest>();
                        GetRequest getReq = new GetRequest(id, bucket);
                        getReq.collectionId(collectionId);
                        requests.add(getReq);
                        for (int i = 0; i < max; i++) {
                            ReplicaGetRequest replicaGetRequest = new ReplicaGetRequest(id, bucket, (short) (i + 1));
                            replicaGetRequest.collectionId(collectionId);
                            requests.add(replicaGetRequest);
                        }
                        return Observable.from(requests);
                    }
                });
    }

