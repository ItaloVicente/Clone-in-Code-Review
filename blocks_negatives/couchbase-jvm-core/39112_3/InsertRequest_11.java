public class InsertRequest extends AbstractBinaryRequest {

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

