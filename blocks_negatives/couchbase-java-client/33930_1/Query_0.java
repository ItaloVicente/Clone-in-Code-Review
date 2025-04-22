    } else if (value instanceof Stale) {
      encoded = value.toString();
    } else if (value instanceof OnError) {
      encoded = value.toString();
    } else if (StringUtils.isJsonObject(value.toString())) {
      encoded = value.toString();
    } else if(value.toString().startsWith("\"")) {
