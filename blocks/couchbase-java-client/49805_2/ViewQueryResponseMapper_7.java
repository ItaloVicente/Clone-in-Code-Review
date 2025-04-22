                    public Observable<AsyncSpatialViewRow> call(final JsonObject row) {
                        final String id = row.getString("id");

                        if (query.isIncludeDocs()) {
                            return bucket.get(id, query.includeDocsTarget()).map(new Func1<Document<?>, AsyncSpatialViewRow>() {
                                @Override
                                public AsyncSpatialViewRow call(Document<?> document) {
                                    return new DefaultAsyncSpatialViewRow(bucket, row.getString("id"), row.getArray("key"),
                                        row.get("value"), row.getObject("geometry"), document);
                                }
                            });
                        } else {
                            return Observable.just((AsyncSpatialViewRow)
                                new DefaultAsyncSpatialViewRow(bucket, row.getString("id"), row.getArray("key"),
                                    row.get("value"), row.getObject("geometry"), null)
                            );
                        }
