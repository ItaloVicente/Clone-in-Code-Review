import com.couchbase.client.core.message.document.CoreDocument;

public class ReplaceRequest extends AbstractCoreDocumentBinaryRequest
{

	public ReplaceRequest(final String key, final CoreDocument document, final String bucket)
	{
        super(key, document, bucket);
	}
