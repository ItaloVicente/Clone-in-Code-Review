    public UpsertRequest(final String key, final ByteBuf content, final String bucket) {
        this(key, content, 0, 0, bucket, false);
    }

    public UpsertRequest(final String key, final ByteBuf content, final int exp, final int flags, final String bucket) {
        this(key, content, exp, flags, bucket, false);
    }
    /**
     * Creates a new {@link UpsertRequest}.
     *
     * @param key the key of the document.
     * @param content the content of the document.
     * @param exp the expiration time.
     * @param flags optional flags.
     * @param bucket the the name of the bucket.
     */
    public UpsertRequest(final String key, final ByteBuf content, final int exp, final int flags, final String bucket,
        final boolean json) {
        super(key, bucket, null);
        this.content = content;
        this.expiration = exp;
        this.flags = flags;
        this.json = json;
    }

    /**
     * Returns the expiration time for this document.
     *
     * @return the expiration time.
     */
    public int expiration() {
        return expiration;
    }

    /**
     * Returns the flags for this document.
     *
     * @return the flags.
     */
    public int flags() {
        return flags;
