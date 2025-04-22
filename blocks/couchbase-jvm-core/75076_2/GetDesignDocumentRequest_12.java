        super(bucket, null, password);
        this.name = name;
        this.development = development;
    }

    public GetDesignDocumentRequest(String name, boolean development, String bucket, String username, String password) {
        super(bucket, username, password);
