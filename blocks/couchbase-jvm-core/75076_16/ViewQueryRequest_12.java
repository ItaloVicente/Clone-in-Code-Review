    public ViewQueryRequest(String design, String view, boolean development, boolean spatial, String query, String keys,
                            String bucket, String password) {
        this(design, view, development, spatial, query, keys, bucket, bucket, password);
    }

    public ViewQueryRequest(String design, String view, boolean development, boolean spatial, String query, String keys,
                            String bucket, String username, String password) {
        super(bucket, username, password);
