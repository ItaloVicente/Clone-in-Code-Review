    @Override
    public DefaultAsyncSearchIndexManager asyncSearchIndexManager() {
        return new DefaultAsyncSearchIndexManager();
    }

    public class DefaultAsyncSearchIndexManager implements AsyncSearchIndexManager {

        public Observable<JsonObject> listIndexDefinitions() {
            return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<ListSearchIndexDefinitionResponse>>() {
                        @Override
                        public Observable<ListSearchIndexDefinitionResponse> call(Boolean aBoolean) {
                            return core.send(new ListSearchIndexDefinitionRequest(username, password));
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<ListSearchIndexDefinitionResponse, JsonObject>() {
                                @Override
                                public JsonObject call(ListSearchIndexDefinitionResponse response) {
                                    if (!response.status().isSuccess()) {
                                        throw new CouchbaseException(response.payload());
                                    }
                                    return JsonObject.fromJson(response.payload());
                                }});

        }

        public Observable<JsonObject> listIndexDefinition(final String indexName) {
            return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<ListSearchIndexDefinitionResponse>>() {
                        @Override
                        public Observable<ListSearchIndexDefinitionResponse> call(Boolean aBoolean) {
                            return core.send(new ListSearchIndexDefinitionRequest(username, password, indexName));
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<ListSearchIndexDefinitionResponse, JsonObject>() {
                        @Override
                        public JsonObject call(ListSearchIndexDefinitionResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new CouchbaseException(response.payload());
                            }
                            return JsonObject.fromJson(response.payload());
                        }});
        }

        public Observable<JsonObject> createIndex(final SearchIndexDefinitionBuilder definition) {
            return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<UpsertSearchIndexResponse>>() {
                        @Override
                        public Observable<UpsertSearchIndexResponse> call(Boolean aBoolean) {
                            return core.send(new UpsertSearchIndexRequest(definition.getIndexName(), definition.toString(), username, password));
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<UpsertSearchIndexResponse, JsonObject>() {
                        @Override
                        public JsonObject call(UpsertSearchIndexResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new CouchbaseException(response.payload());
                            }
                            return JsonObject.fromJson(response.payload());
                        }});
        }

        public Observable<Boolean> deleteIndex(final String indexName) {
            return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<RemoveSearchIndexResponse>>() {
                        @Override
                        public Observable<RemoveSearchIndexResponse> call(Boolean aBoolean) {
                            return core.send(new RemoveSearchIndexRequest(indexName, username, password));
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<RemoveSearchIndexResponse, Boolean>() {
                        @Override
                        public Boolean call(RemoveSearchIndexResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new CouchbaseException(response.payload());
                            }
                            return true;
                        }
                    });
        }

        public Observable<Integer> getIndexedDocumentCount(final String indexName) {
            return ensureServiceEnabled()
                    .flatMap(new Func1<Boolean, Observable<GetIndexDocumentCountResponse>>() {
                        @Override
                        public Observable<GetIndexDocumentCountResponse> call(Boolean aBoolean) {
                            return core.send(new GetIndexDocumentCountRequest(username, password, indexName));
                        }
                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .map(new Func1<GetIndexDocumentCountResponse, Integer>() {
                        @Override
                        public Integer call(GetIndexDocumentCountResponse response) {
                            if (!response.status().isSuccess()) {
                                throw new CouchbaseException(response.payload());
                            }
                            JsonObject jsonObject = JsonObject.fromJson(response.payload());
                            return jsonObject.getInt("count");
                        }
                    });
        }

    }
