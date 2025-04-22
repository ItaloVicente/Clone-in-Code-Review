    try {
      return mapper.readValue(buffer.toString(CharsetUtil.UTF_8),
        JsonObject.class);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
