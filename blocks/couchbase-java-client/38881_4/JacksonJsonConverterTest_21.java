    final JsonArray children = JsonArray.empty()
        .add(JsonObject.empty().put("name", "Jane Doe").put("age", 25))
        .add(JsonObject.empty().put("name", "Tom Doe").put("age", 13));
    final JsonObject object = JsonObject.empty()
        .put("firstname", "John")
        .put("lastname", "Doe")
        .put("colors", JsonArray.empty().add("red").add("blue"))
        .put("children", children)
        .put("active", true);
    final CoreDocument coreDocument = converter.encode(JsonDocument.create(null, object));
    final String expected = "{\"firstname\":\"John\"," +
        "\"children\":[{\"name\":\"Jane Doe\",\"age\":25},{\"name\":\"Tom Doe\",\"age\":13}]," +
        "\"active\":true,\"colors\":[\"red\",\"blue\"],\"lastname\":\"Doe\"}";

    assertEquals(expected, coreDocument.content().toString(CharsetUtil.UTF_8));
