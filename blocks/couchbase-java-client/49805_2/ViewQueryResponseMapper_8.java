                    public Observable<AsyncViewRow> call(final JsonObject row) {
                        final String id = row.getString("id");

                        if (query.isIncludeDocs()) {
                            return bucket.get(id, query.includeDocsTarget()).map(new Func1<Document<?>, AsyncViewRow>() {
                                @Override
                                public AsyncViewRow call(Document<?> document) {
                                    return new DefaultAsyncViewRow(bucket, id, row.get("key"), row.get("value"), document);
                                }
                            });
                        } else {
                            return Observable.just((AsyncViewRow)
                                new DefaultAsyncViewRow(bucket, id, row.get("key"), row.get("value"), null)
                            );
                        }
