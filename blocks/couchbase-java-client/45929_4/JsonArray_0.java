    public static JsonArray fromJson(String s) {
        try {
            return CouchbaseAsyncBucket.JSON_ARRAY_TRANSCODER.stringToJsonArray(s);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert string to JsonArray", e);
        }
    }

