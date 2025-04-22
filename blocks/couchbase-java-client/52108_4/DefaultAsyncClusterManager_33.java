                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new InsertBucketRequest(sb.toString(), username, password);
                        }
                    });
