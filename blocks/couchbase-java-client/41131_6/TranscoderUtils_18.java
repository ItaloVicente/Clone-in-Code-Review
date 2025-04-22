package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.StringDocument;
import com.couchbase.client.java.error.TranscodingException;

public class StringTranscoder extends AbstractTranscoder<StringDocument, String> {

    @Override
    protected StringDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags,
        ResponseStatus status) throws Exception {
        if (!TranscoderUtils.hasStringFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-String document for "
                + "id " + id + ", could not decode.");
        }
        return newDocument(id, expiry, content.toString(CharsetUtil.UTF_8), cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(StringDocument document) throws Exception {
        return Tuple.create(Unpooled.copiedBuffer(document.content(), CharsetUtil.UTF_8), TranscoderUtils.STRING_COMMON_FLAGS);
    }

    @Override
    public StringDocument newDocument(String id, int expiry, String content, long cas) {
        return StringDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<StringDocument> documentType() {
        return StringDocument.class;
    }
}
