                                        final short rnum = i;
                                        Observable<ObserveResponse> res = core.send(new RequestFactory() {
                                            @Override
                                            public CouchbaseRequest call() {
                                                return new ObserveRequest(id, cas, false, rnum, bucket);
                                            }
                                        });
