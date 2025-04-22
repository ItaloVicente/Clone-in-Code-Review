    public static JsonObject fromJson(String s) {
        try {
            return CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(s);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert string to JsonObject", e);
        }
    }

