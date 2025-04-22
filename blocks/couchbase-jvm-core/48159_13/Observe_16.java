                                Observable<ObserveResponse> masterRes = core.send(new RequestFactory() {
                                    @Override
                                    public CouchbaseRequest call() {
                                        return new ObserveRequest(id, cas, true, (short) 0, bucket);
                                    }
                                });
