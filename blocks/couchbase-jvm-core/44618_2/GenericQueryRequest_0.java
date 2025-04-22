
    public boolean isJsonFormat() {
        return jsonFormat;
    }

    public static GenericQueryRequest simpleStatement(String statement, String bucket, String password) {
        return new GenericQueryRequest(statement, false, bucket, password);
    }

    public static GenericQueryRequest jsonQuery(String jsonQuery, String bucket, String password) {
        return new GenericQueryRequest(jsonQuery, true, bucket, password);
    }
