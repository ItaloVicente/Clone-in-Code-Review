                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new RemoveBucketRequest(name, username, password);
                        }
                    });
