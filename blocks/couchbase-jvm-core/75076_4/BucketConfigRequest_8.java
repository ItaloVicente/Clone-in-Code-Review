        super(bucket, bucket, password);
        this.hostname = hostname;
        this.path = path;
    }

    public BucketConfigRequest(String path, InetAddress hostname, String bucket, String username, String password) {
        super(bucket, username, password);
