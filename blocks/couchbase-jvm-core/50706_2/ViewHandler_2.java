    private String encodeKeysPost(String keys) {
        return "{\"keys\":" + keys + "}";
    }

    private String encodeKeysGet(String keys) {
        try {
            return URLEncoder.encode(keys, "UTF-8");
        } catch(Exception ex) {
            throw new RuntimeException("Could not prepare view argument: " + ex);
        }
    }

