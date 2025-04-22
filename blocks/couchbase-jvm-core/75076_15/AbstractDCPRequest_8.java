        this(bucket, bucket, password, observable);
    }

    public AbstractDCPRequest(String bucket, String username, String password, Subject<CouchbaseResponse,
            CouchbaseResponse> observable) {
        super(bucket, username, password, observable);
