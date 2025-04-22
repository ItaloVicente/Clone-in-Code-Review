    public void loadPool(Pool pool, String sPool) throws ParseException {
        try {
            JSONObject poolJO = new JSONObject(sPool);
            JSONObject poolBucketsJO = poolJO.getJSONObject("buckets");
            URI bucketsUri = new URI((String) poolBucketsJO.get("uri"));
            pool.setBucketsUri(bucketsUri);
        } catch (JSONException e) {
            throw new ParseException(e.getMessage(), 0);
        } catch (URISyntaxException e) {
            throw new ParseException(e.getMessage(), 0);
        }
