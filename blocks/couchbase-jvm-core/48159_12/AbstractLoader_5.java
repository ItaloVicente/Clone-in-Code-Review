                    return cluster.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new AddNodeRequest(address);
                        }
                    });
