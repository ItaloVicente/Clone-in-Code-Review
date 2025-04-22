    private final long cas;
    private final int flags;

    public GetResponse(final ResponseStatus status, final long cas, final int flags, final String bucket, final ByteBuf content, final CouchbaseRequest request) {
        super(status, bucket, content, request);
        this.cas = cas;
        this.flags = flags;
    }

    public long cas() {
        return cas;
    }

    public int flags() {
        return flags;
