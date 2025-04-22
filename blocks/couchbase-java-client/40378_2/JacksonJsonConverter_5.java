      return Unpooled.copiedBuffer(encodeToString(content), CharsetUtil.UTF_8);
  }

  public String encodeToString(JsonObject content) {
      try {
          return mapper.writeValueAsString(content);
      } catch (JsonProcessingException e) {
          throw new IllegalStateException(e);
      }
