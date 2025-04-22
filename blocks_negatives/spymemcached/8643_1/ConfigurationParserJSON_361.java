    public Map<String, Bucket> parseBuckets(String buckets) throws ParseException {
        Map<String, Bucket> bucketsMap = new HashMap<String, Bucket>();
        try {
            JSONArray bucketsJA = new JSONArray(buckets);
            for (int i = 0; i < bucketsJA.length(); ++i) {
                JSONObject bucketJO = bucketsJA.getJSONObject(i);
                Bucket bucket = parseBucketFromJSON(bucketJO);
                bucketsMap.put(bucket.getName(), bucket);
            }
        } catch (JSONException e) {
            throw new ParseException(e.getMessage(), 0);
        }
        return bucketsMap;
