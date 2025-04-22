      return parseBucketFromJSON(new JSONObject(bucketJson), null);
    } catch (JSONException e) {
      throw new ParseException(e.getMessage(), 0);
    }
  }

  public Bucket updateBucket(String bucketJson, Bucket currentBucket)
    throws ParseException {
    try {
      return parseBucketFromJSON(new JSONObject(bucketJson), currentBucket);
