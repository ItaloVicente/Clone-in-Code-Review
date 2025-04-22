public class GetRequest extends AbstractCouchbaseRequest implements BinaryRequest {

    /**
     * The key of the document
     */
    private final String key;

    private short partition = 0;
