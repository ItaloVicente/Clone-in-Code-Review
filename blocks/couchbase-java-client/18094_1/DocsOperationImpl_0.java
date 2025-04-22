
  @Override
  protected ViewResponseWithDocs parseError(String json, int errorcode)
      throws ParseException {
    final Collection<ViewRow> rows = new LinkedList<ViewRow>();
    final Collection<RowError> errors = new LinkedList<RowError>();
    if (json != null) {
      try {
        JSONObject base = new JSONObject(json);
        if (base.has("error") && base.has("reason")) {
          String error = base.getString("error");
          String reason = base.getString("reason");
          errors.add(new RowError(error, reason));
        }
      } catch (JSONException e) {
        errors.add(new RowError(Integer.toString(errorcode), "No reason given"));
      }
    }
    return new ViewResponseWithDocs(rows, errors);
  }
