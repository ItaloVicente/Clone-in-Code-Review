    JsonArray children = JsonArray.empty()
      .add(JsonObject.empty().put("name", "Jane Doe").put("age", 25))
      .add(JsonObject.empty().put("name", "Tom Doe").put("age", 13));

    JsonObject user = JsonObject.empty()
      .put("firstname", "John")
      .put("lastname", "Doe")
      .put("colors", JsonArray.empty().add("red").add("blue"))
      .put("children", children)
      .put("active", true);

    String expected = "{\"colors\":[\"red\",\"blue\"],\"active\":true, +
-      \"children\":[{\"age\":25,\"name\":\"Jane Doe\"},{\"age\":13,\"name\":" +
      "\"Tom Doe\"}],\"lastname\":\"Doe\",\"firstname\":\"John\"}";
    ByteBuf buf = converter.encode(user);
    assertEquals(expected, buf.toString(CharsetUtil.UTF_8));
