  }

  private Bucket parseBucketFromJSON(JSONObject bucketJO)
    throws ParseException {
    try {
      String bucketname = bucketJO.get("name").toString();
      String streamingUri = bucketJO.get("streamingUri").toString();
      ConfigFactory cf = new DefaultConfigFactory();
      Config config = cf.create(bucketJO);
      List<Node> nodes = new ArrayList<Node>();
      JSONArray nodesJA = bucketJO.getJSONArray("nodes");
      for (int i = 0; i < nodesJA.length(); ++i) {
        JSONObject nodeJO = nodesJA.getJSONObject(i);
        String statusValue = nodeJO.get("status").toString();
        Status status = null;
