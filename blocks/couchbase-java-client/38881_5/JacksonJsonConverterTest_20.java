    final JsonObject inner = JsonObject.empty().put("foo", "bar");
    final JsonObject object = JsonObject.empty().put("object", JsonObject.empty().put("inner", inner));
    final CoreDocument coreDocument = converter.encode(JsonDocument.create(null, object));
    final String expected = "{\"object\":{\"inner\":{\"foo\":\"bar\"}}}";

    assertEquals(expected, coreDocument.content().toString(CharsetUtil.UTF_8));
