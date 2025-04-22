    try {
      return Unpooled.copiedBuffer(mapper.writeValueAsString(content),
        CharsetUtil.UTF_8);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
