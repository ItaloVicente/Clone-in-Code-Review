      Map<String, Bucket> bucketsMap = new HashMap<String, Bucket>();
      JSONArray allBuckets = new JSONArray(bucketsJson);

      for (int i = 0; i < allBuckets.length(); i++) {
        JSONObject currentBucket = allBuckets.getJSONObject(i);
        Bucket bucket = parseBucketFromJSON(currentBucket);
