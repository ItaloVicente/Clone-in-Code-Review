    public InsertRequest(final String key, final ByteBuf content, final int exp, final int flags, final String bucket, final boolean json) {
        super(key, bucket, null);
        this.content = content;
        this.expiration = exp;
        this.flags = flags;
        this.json = json;
