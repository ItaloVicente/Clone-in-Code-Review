  }

  @Override
  public Config create(JSONObject jsonObject) {
    try {
      return parseJSON(jsonObject);
    } catch (JSONException e) {
      throw new ConfigParsingException("Exception parsing JSON data: "
        + jsonObject, e);
