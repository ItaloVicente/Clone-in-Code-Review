    private static void putIfNotNull(final Map<String, Object> map, final String key, final Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    private static String formatServiceType(final CouchbaseRequest request) {
