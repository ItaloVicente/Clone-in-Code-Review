        this(key, bucket, password, AsyncSubject.<CouchbaseResponse>create());
    }

    protected AbstractKeyValueRequest(String key, String bucket, String password,
                                      Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        super(bucket, password, observable);
