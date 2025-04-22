  private List<URL> populateCouchServers(JSONArray nodes) throws JSONException{
    List<URL> nodeNames = new ArrayList<URL>();
    for (int i = 0; i < nodes.length(); i++) {
      JSONObject node = nodes.getJSONObject(i);
      if (node.has("couchApiBase")) {
        try {
          nodeNames.add(new URL(node.getString("couchApiBase")));
        } catch (MalformedURLException e) {
          throw new JSONException("Got bad couchApiBase URL from config");
        }
      }
    }
    return nodeNames;
  }

