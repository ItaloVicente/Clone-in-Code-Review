  @Test
  public void shouldInsertAndGet() {
    JsonObject content = JsonObject.empty().put("hello", "world");
    final JsonDocument doc = JsonDocument.create("insert", content);
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
    assertEquals(content.getString("hello"), response.content().getString("hello"));
  }
