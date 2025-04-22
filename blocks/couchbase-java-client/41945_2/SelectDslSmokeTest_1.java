package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.error.TranscodingException;

public class RawJsonTranscoder extends AbstractTranscoder<RawJsonDocument, String> {

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(RawJsonDocument document) throws Exception {
        return Tuple.create(Unpooled.copiedBuffer(document.content(), CharsetUtil.UTF_8),
            TranscoderUtils.JSON_COMPAT_FLAGS);
    }

    @Override
    protected RawJsonDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags,
        ResponseStatus status) throws Exception {
        if (!TranscoderUtils.hasJsonFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-JSON document for "
                + "id " + id + ", could not decode.");
        }

        String converted = content.toString(CharsetUtil.UTF_8);
        content.release();
        return newDocument(id, expiry, converted, cas);
    }

    @Override
    public RawJsonDocument newDocument(String id, int expiry, String content, long cas) {
        return RawJsonDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<RawJsonDocument> documentType() {
        return RawJsonDocument.class;
    }
}
