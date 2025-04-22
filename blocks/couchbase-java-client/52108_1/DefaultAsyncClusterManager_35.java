                        .<AddNodeResponse>send(new RequestFactory() {
                            @Override
                            public CouchbaseRequest call() {
                                return new AddNodeRequest(hostname);
                            }
                        })
