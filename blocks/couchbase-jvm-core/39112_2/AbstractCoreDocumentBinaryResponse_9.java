package com.couchbase.client.core.message.binary;

import com.couchbase.client.core.message.document.CoreDocument;

public abstract class AbstractCoreDocumentBinaryRequest extends AbstractBinaryRequest {

    private final CoreDocument document;

    public AbstractCoreDocumentBinaryRequest(final CoreDocument document, final String bucket) {
        super(bucket, null);
        this.document = document;
    }

    public CoreDocument document() {
        return document;
    }

    @Override
    public String key() {
        return document.id();
    }

}
