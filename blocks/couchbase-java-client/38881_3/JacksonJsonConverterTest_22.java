    final String input = "{\"firstname\":\"John\"," +
        "\"children\":[{\"name\":\"Jane Doe\",\"age\":25},{\"name\":\"Tom Doe\",\"age\":13}]," +
        "\"active\":true,\"colors\":[\"red\",\"blue\"],\"lastname\":\"Doe\"}";
    final JsonObject user = converter.decode(input);
    final JsonObject child0 = user.getArray("children").getObject(0);
    final JsonObject child1 = user.getArray("children").getObject(1);
