public class UpsertRequest extends AbstractBinaryRequest {

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

    public UpsertRequest(final String key, final ByteBuf content, final String bucket, final boolean json) {
        this(key, content, 0, 0, bucket, json);
    }
