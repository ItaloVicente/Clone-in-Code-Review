    final String input = "{\"colors\":[\"red\",\"blue\"],\"active\":true," +
        "\"children\":[{\"age\":25,\"name\":\"Jane Doe\"},{\"age\":13,\"name\":" +
        "\"Tom Doe\"}],\"lastname\":\"Doe\",\"firstname\":\"John\"}";
    final ByteBuf buf = Unpooled.copiedBuffer(input, CharsetUtil.UTF_8);
    final JsonObject user = converter.decode(buf, 0);
    final JsonObject child0 = user.getArray("children").getObject(0);
    final JsonObject child1 = user.getArray("children").getObject(1);
