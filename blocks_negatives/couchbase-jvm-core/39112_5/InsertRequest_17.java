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
