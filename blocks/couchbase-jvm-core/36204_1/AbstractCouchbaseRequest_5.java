
    private final String bucket;

    private final String password;

    protected AbstractCouchbaseRequest(final String bucket, final String password) {
        this.bucket = bucket;
        this.password = password;
    }
