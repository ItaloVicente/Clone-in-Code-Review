    @Override
    public Observable<JsonDocument> getReplica(final String id, final ReplicaMode type) {
        return getReplica(id, JsonDocument.class, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getReplica(final D document, final ReplicaMode type) {
        return (Observable<D>) getReplica(document.id(), document.getClass(), type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> getReplica(final String id, final Class<D> target,
        final ReplicaMode type) {

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

        return incoming.map(new Func1<GetResponse, D>() {
            @Override
            public D call(final GetResponse response) {
                Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
                Object content = response.status() == ResponseStatus.SUCCESS ? converter.decode(response.content()) : null;
                return (D) converter.newDocument(id, content, response.cas(), 0, response.status());
            }
        });
    }

