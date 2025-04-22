                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new ClusterConfigRequest(username, password);
                        }
                    });
