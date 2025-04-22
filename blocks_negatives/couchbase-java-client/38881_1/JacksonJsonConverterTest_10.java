    JsonObject inner = JsonObject.empty().put("foo", "bar");
    JsonObject object = JsonObject
      .empty()
      .put("object", JsonObject.empty().put("inner", inner));

    ByteBuf buf = converter.encode(object);
    String expected = "{\"object\":{\"inner\":{\"foo\":\"bar\"}}}";
    assertEquals(expected, buf.toString(CharsetUtil.UTF_8));
