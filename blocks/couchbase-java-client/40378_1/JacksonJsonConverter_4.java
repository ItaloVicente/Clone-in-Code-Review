        return decode(buffer.toString(CharsetUtil.UTF_8));
  }

  public JsonObject decode(String buffer) {
      try {
          return mapper.readValue(buffer, JsonObject.class);
      } catch (IOException e) {
          throw new IllegalStateException(e);
      }
