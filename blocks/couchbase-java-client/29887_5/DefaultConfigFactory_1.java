      return parseJSON(source, null);
    } catch (JSONException e) {
      throw new ConfigParsingException("Exception parsing JSON data: "
        + source, e);
    }
  }

  @Override
  public Config create(JSONObject source, Config oldConfig) {
    try {
      return parseJSON(source, oldConfig);
