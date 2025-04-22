    /**
     * The content of the document.
     */
    private final ByteBuf content;

    /**
     * The optional expiration time.
     */
    private final int expiration;

    /**
     * The optional flags.
     */
    private final int flags;

    private final boolean json;

    /**
     * Creates a new {@link InsertRequest}.
     *
     * @param key the key of the document.
     * @param content the content of the document.
     * @param bucket the name of the bucket.
     */
    public InsertRequest(final String key, final ByteBuf content, final String bucket) {
        this(key, content, 0, 0, bucket, false);
    }

    public InsertRequest(final String key, final ByteBuf content, final String bucket, final boolean json) {
        this(key, content, 0, 0, bucket, json);
    }


    /**
     * Creates a new {@link InsertRequest}.
     *
     * @param key the key of the document.
     * @param content the content of the document.
     * @param exp the expiration time.
     * @param flags optional flags.
     * @param bucket the the name of the bucket.
     */
    public InsertRequest(final String key, final ByteBuf content, final int exp, final int flags, final String bucket, final boolean json) {
        super(key, bucket, null);
        this.content = content;
        this.expiration = exp;
        this.flags = flags;
        this.json = json;
    }

    public InsertRequest(final String key, final ByteBuf content, final int exp, final int flags, final String bucket) {
        this(key, content, exp, flags, bucket, false);
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
    }

    /**
     * The content of the document.
     *
     * @return the content.
     */
    public ByteBuf content() {
        return content;
    }

    public boolean isJson() {
        return json;
    }
