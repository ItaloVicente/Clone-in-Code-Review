  private View parseDesignDocumentForView(String dn, String ddn,
      String viewname, String json) throws ParseException {
    View view = null;
    if (json != null) {
      try {
        JSONObject base = new JSONObject(json);
        if (base.has("error")) {
          return null;
        }
        if (base.has("views")) {
          JSONObject views = base.getJSONObject("views");
          Iterator<?> itr = views.keys();
          while (itr.hasNext()) {
            String curView = (String) itr.next();
            if (curView.equals(viewname)) {
              boolean map = views.getJSONObject(curView).has("map");
              boolean reduce = views.getJSONObject(curView).has("reduce");
              view = new View(dn, ddn, viewname, map, reduce);
              break;
            }
          }
        }
      } catch (JSONException e) {
        throw new ParseException("Cannot read json: " + json, 0);
      }
    }
    return view;
  }
