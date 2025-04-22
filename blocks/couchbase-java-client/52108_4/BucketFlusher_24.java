                public Observable<GetResponse> call(final String id) {
                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new GetRequest(id, bucket);
                        }
                    });
