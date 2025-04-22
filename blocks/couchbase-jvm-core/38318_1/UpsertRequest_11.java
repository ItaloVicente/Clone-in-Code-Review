    private final boolean json;

    public UpsertRequest(final String key, final ByteBuf content, final String bucket, final boolean json) {
        this(key, content, 0, 0, bucket, json);
    }

