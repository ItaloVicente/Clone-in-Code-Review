                    .<BucketStreamingResponse>send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new BucketStreamingRequest(VERBOSE_PATH, name, password);
                        }
                    })
