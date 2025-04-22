                    .<BucketStreamingResponse>send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new BucketStreamingRequest(TERSE_PATH, name, password);
                        }
                    })
