    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getAndTouch(final String id, final int expiry, final Class<D> target) {
        return core.<GetResponse>send(new GetRequest(id, bucket, false, true, expiry))
            .filter(new Func1<GetResponse, Boolean>() {
                @Override
                public Boolean call(GetResponse getResponse) {
                    return getResponse.status() == ResponseStatus.SUCCESS;
                }
            })
            .map(new Func1<GetResponse, D>() {
                @Override
                public D call(final GetResponse response) {
                    Transcoder<?, Object> transcoder = (Transcoder<?, Object>) transcoders.get(target);
                    return (D) transcoder.decode(id, response.content(), response.cas(), 0, response.flags(), response.status());
                }
            });
    }

    @Override
    public Observable<JsonDocument> getFromReplica(final String id, final ReplicaMode type) {
        return getFromReplica(id, type, JsonDocument.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getFromReplica(final D document, final ReplicaMode type) {
        return (Observable<D>) getFromReplica(document.id(), type, document.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getFromReplica(final String id, final ReplicaMode type,
        final Class<D> target) {

        Observable<GetResponse> incoming;
        if (type == ReplicaMode.ALL) {
            incoming = core
                .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
                .map(new Func1<GetClusterConfigResponse, Integer>() {
                    @Override
                    public Integer call(GetClusterConfigResponse response) {
                        CouchbaseBucketConfig conf = (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                        return conf.numberOfReplicas();
                    }
                }).flatMap(new Func1<Integer, Observable<BinaryRequest>>() {
                    @Override
                    public Observable<BinaryRequest> call(Integer max) {
                        List<BinaryRequest> requests = new ArrayList<BinaryRequest>();

                        requests.add(new GetRequest(id, bucket));
                        for (int i = 0; i < max; i++) {
                            requests.add(new ReplicaGetRequest(id, bucket, (short)(i+1)));
                        }
                        return Observable.from(requests);
                    }
                }).flatMap(new Func1<BinaryRequest, Observable<GetResponse>>() {
                    @Override
                    public Observable<GetResponse> call(BinaryRequest req) {
                        return core.send(req);
                    }
                });
        } else {
            incoming = core.send(new ReplicaGetRequest(id, bucket, (short) type.ordinal()));
        }

        return incoming
            .filter(new Func1<GetResponse, Boolean>() {
                @Override
                public Boolean call(GetResponse getResponse) {
                    return getResponse.status() == ResponseStatus.SUCCESS;
                }
            })
            .map(new Func1<GetResponse, D>() {
                @Override
                public D call(final GetResponse response) {
                    Transcoder<?, Object> transcoder = (Transcoder<?, Object>) transcoders.get(target);
                    return (D) transcoder.decode(id, response.content(), response.cas(), 0, response.flags(), response.status());
                }
            });
