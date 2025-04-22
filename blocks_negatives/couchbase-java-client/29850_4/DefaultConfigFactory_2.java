  private List<VBucket> populateVbuckets(JSONArray jsonVbuckets)
    throws JSONException {
    List<VBucket> vBuckets = new ArrayList<VBucket>();
    for (int i = 0; i < jsonVbuckets.length(); i++) {
      JSONArray rows = jsonVbuckets.getJSONArray(i);
      int master = rows.getInt(0);
      int[] replicas = new int[VBucket.MAX_REPLICAS];
