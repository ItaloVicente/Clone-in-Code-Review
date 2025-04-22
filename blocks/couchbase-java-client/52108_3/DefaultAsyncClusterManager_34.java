                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new UpdateBucketRequest(settings.name(), sb.toString(), username, password);
                        }
                    });
