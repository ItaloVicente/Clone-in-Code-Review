    assertEquals(ResponseStatus.SUCCESS, response.status());
  }

  @Test
  public void shouldUpsertAndReplace() {
    JsonObject content = JsonObject.empty().put("hello", "world");
    final JsonDocument doc = JsonDocument.create("upsert-r", content);
    JsonDocument response = bucket.upsert(doc)
      .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
        @Override
        public Observable<JsonDocument> call(JsonDocument document) {
          return bucket.get("upsert-r");
        }
      })
      .toBlockingObservable()
      .single();
    assertEquals(content.getString("hello"), response.content().getString("hello"));

    JsonDocument updated = JsonDocument.from(response, JsonObject.empty().put("hello", "replaced"));
    response = bucket.replace(updated)
      .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
        @Override
        public Observable<JsonDocument> call(JsonDocument document) {
          return bucket.get("upsert-r");
        }
      })
      .toBlockingObservable()
      .single();
    assertEquals("replaced", response.content().getString("hello"));
