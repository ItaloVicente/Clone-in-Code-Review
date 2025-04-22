        return new GenericQueryRequest(jsonQuery, true, bucket, bucket, password, null);
    }


    public static GenericQueryRequest jsonQuery(String jsonQuery, String bucket, String username, String password) {
        return new GenericQueryRequest(jsonQuery, true, bucket, username, password, null);
