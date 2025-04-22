  private List<String> populateServersForCouchbaseBucket(JSONArray nodes)
    throws JSONException {
    int nodeSize = nodes.length();
    List<String> serverNames = new ArrayList<String>(nodeSize);
    for (int i = 0; i < nodeSize; i++) {
      serverNames.add(nodes.getString(i));
