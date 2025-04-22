    final JsonObject inner = JsonObject.empty().put("foo", "bar");
    final JsonObject object = JsonObject.empty().put("object", JsonObject.empty().put("inner", inner));
    final CachedData cachedData = converter.encode(object);
    final String expected = "{\"object\":{\"inner\":{\"foo\":\"bar\"}}}";

    assertEquals(expected, cachedData.getBuffer().toString(CharsetUtil.UTF_8));
