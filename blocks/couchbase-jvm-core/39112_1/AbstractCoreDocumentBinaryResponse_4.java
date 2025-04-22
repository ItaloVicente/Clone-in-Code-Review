package com.couchbase.client.core.message.binary;

import com.couchbase.client.core.message.document.CoreDocument;

public abstract class AbstractCoreDocumentBinaryRequest extends AbstractBinaryRequest
{

	private final CoreDocument document;

	public AbstractCoreDocumentBinaryRequest(final String key, final CoreDocument document, final String bucket)
	{
		super(key, bucket, null);
		this.document = document;
	}

	public CoreDocument document()
	{
		return document;
	}

}
