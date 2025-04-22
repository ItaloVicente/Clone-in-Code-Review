package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.java.document.ByteArrayDocument;
import com.couchbase.client.java.error.TranscodingException;

public class ByteArrayTranscoder extends AbstractTranscoder<ByteArrayDocument, byte[]> {

    @Override
    protected ByteArrayDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags,
        ResponseStatus status) throws Exception {
        if (!TranscoderUtils.hasBinaryFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-binary " +
                "document for id " + id + ", could not decode.");
        }
        byte[] data = new byte[content.readableBytes()];
        content.readBytes(data);
        return newDocument(id, expiry, data, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(ByteArrayDocument document) throws Exception {
        return Tuple.create(
            Unpooled.wrappedBuffer(document.content()),
            TranscoderUtils.BINARY_COMPAT_FLAGS
        );
    }

    @Override
    public ByteArrayDocument newDocument(String id, int expiry, byte[] content, long cas) {
        return ByteArrayDocument.create(id, expiry, content, cas);
    }

    @Override
    public ByteArrayDocument newDocument(String id, int expiry, byte[] content, long cas,
        MutationToken mutationToken) {
        return ByteArrayDocument.create(id, expiry, content, cas, mutationToken);
    }

    @Override
    public Class<ByteArrayDocument> documentType() {
        return ByteArrayDocument.class;
    }
}
