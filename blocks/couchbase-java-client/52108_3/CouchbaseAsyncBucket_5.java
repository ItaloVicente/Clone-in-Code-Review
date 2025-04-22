                    public Observable<GetResponse> call(final BinaryRequest req) {
                        return core.send(new RequestFactory() {
                            @Override
                            public CouchbaseRequest call() {
                                return req;
                            }
                        });
