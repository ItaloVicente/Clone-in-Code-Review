        JsonDocument response = bucket()
            .insert(doc)
            .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(JsonDocument document) {
                    return bucket().get("insert");
                }
            })
            .toBlocking()
            .single();
