package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.document.BinaryDocument;
import com.couchbase.client.java.error.TranscodingException;

public class BinaryTranscoder extends AbstractTranscoder<BinaryDocument, ByteBuf> {

    @Override
    protected BinaryDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags,
        ResponseStatus status) throws Exception {
        if (!TranscoderUtils.hasBinaryFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-binary " +
                "document for id " + id + ", could not decode.");
        }
        return BinaryDocument.create(id, expiry, content, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(BinaryDocument document) throws Exception {
        return Tuple.create(document.content(), TranscoderUtils.BINARY_COMPAT_FLAGS);
    }

    @Override
    public BinaryDocument newDocument(String id, int expiry, ByteBuf content, long cas) {
        return BinaryDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<BinaryDocument> documentType() {
        return BinaryDocument.class;
    }
}
