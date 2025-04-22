                    return cluster.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new AddServiceRequest(serviceType, bucket, password, port(), response.hostname());
                        }
                    });
