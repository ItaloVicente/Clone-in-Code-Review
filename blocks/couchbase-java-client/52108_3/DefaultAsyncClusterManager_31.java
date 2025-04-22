                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new BucketsConfigRequest(username, password);
                        }
                    });
