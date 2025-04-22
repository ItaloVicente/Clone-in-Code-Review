    public ViewQueryRequest(String design, String view, boolean development, String query, String keys, String bucket,
                            String password) {
        this(design, view, development, false, query, keys, bucket, bucket, password);
    }

    public ViewQueryRequest(String design, String view, boolean development, String query, String keys, String bucket,
                            String username, String password) {
        this(design, view, development, false, query, keys, bucket, username, password);
