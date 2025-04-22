        this.username = bucket;
        this.password = password;
        this.core = core;
    }

    public AsyncRawQueryExecutor(String bucket, String username, String password, ClusterFacade core) {
        this.bucket = bucket;
        this.username = username;
