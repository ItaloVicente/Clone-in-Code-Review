                public Observable<UpsertResponse> call(final String id) {
                    return core.send(new RequestFactory() {
                        @Override
                        public CouchbaseRequest call() {
                            return new UpsertRequest(id, Unpooled.copiedBuffer(id, CharsetUtil.UTF_8), bucket);
                        }
                    });
