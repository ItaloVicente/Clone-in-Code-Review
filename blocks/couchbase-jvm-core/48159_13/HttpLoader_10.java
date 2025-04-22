                    return cluster().send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new BucketConfigRequest(VERBOSE_PATH, hostname, bucket, password);
                        }
                    });
