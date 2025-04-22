  @Test
  public void shouldUpsertAndGet() {
    JsonObject content = JsonObject.empty().put("hello", "world");
    final JsonDocument doc = JsonDocument.create("upsert", content);
    JsonDocument response = bucket().upsert(doc)
      .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
        @Override
        public Observable<JsonDocument> call(JsonDocument document) {
          return bucket().get("upsert");
        }
      })
      .toBlocking()
      .single();
    assertEquals(content.getString("hello"), response.content().getString("hello"));
  }
