    response = bucket().replace(updated)
      .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
        @Override
        public Observable<JsonDocument> call(JsonDocument document) {
          return bucket().get("upsert-r");
        }
      })
      .toBlocking()
      .single();
