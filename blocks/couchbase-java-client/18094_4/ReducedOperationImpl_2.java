
  @Override
  protected void parseError(String json, int errorcode)
    throws ParseException {
    String error = null;
    String reason = null;
    if (json != null) {
      try {
        JSONObject base = new JSONObject(json);
        if (base.has("error") && base.has("reason")) {
          error = base.getString("error");
          reason = base.getString("reason");
        }
      } catch (JSONException e) {
        error = "HTTP " + Integer.toString(errorcode);
        reason = "No extra information given";
      }
    }
    setException(new ViewException(error, reason));
  }
