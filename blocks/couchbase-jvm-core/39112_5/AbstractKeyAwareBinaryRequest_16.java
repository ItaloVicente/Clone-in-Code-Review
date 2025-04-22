package com.couchbase.client.core.message.binary;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.document.CoreDocument;
import io.netty.util.CharsetUtil;

public abstract class AbstractCoreDocumentBinaryResponse extends AbstractCouchbaseResponse implements BinaryResponse {

    private final CoreDocument document;
    private final String bucket;

    public AbstractCoreDocumentBinaryResponse(final CoreDocument document, final String bucket, final CouchbaseRequest request) {
        super(document.status(), request);
        this.document = document;
        this.bucket = bucket;
    }

    @Override
    public CoreDocument document() {
        return document;
    }

    @Override
    public String bucket() {
        return bucket;
    }

    protected String toStringInternal(final String className) {
        return className + '{' + "bucket='" + bucket() + '\'' + ", status=" + status() + ", cas=" + document.cas()
                + ", flags=" + document.flags() + ", request=" + request()
                + ", content=" + document.content().toString(CharsetUtil.UTF_8) + '}';
    }
}
