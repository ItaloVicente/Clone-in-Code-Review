      populatedViewServers, populatedRestEndpoints);
  }

  private List<String> populateRestEndpoints(final JSONArray nodes)
    throws JSONException {
    int nodeSize = nodes.length();
    List<String> endpoints = new ArrayList<String>(nodeSize);
    for (int i = 0; i < nodeSize; i++) {
      JSONObject node = nodes.getJSONObject(i);
      if (node.has("hostname")) {
        endpoints.add("http://" + node.getString("hostname") + "/pools");
      }
    }
    return endpoints;
