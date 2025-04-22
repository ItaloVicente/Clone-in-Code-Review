import io.netty.buffer.ByteBuf;

public class ReplaceRequest extends AbstractBinaryRequest {

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

    /**
     * The cas value.
     */
    private final long cas;

    private final boolean json;
