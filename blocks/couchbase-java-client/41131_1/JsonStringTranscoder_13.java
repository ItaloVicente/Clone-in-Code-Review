package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonDoubleDocument;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.error.TranscodingException;

public class JsonLongTranscoder extends AbstractTranscoder<JsonLongDocument, Long> {

    @Override
    protected JsonLongDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags, ResponseStatus status)
        throws Exception {
        long decoded;

        if (TranscoderUtils.hasCommonFlags(flags) && flags == TranscoderUtils.JSON_COMMON_FLAGS) {
            String val = content.toString(CharsetUtil.UTF_8);
            decoded = Long.valueOf(val);
        } else if (flags == 3 << 8 || flags == 2 << 8) {
            long rv = 0;
            int readable = content.readableBytes();
            for (int i = 0; i < readable; i++) {
                byte b = content.readByte();
                rv = (rv << 8) | (b < 0 ? 256 + b : b);
            }
            decoded = rv;
        } else {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non " +
                "JsonLongDocument id " + id + ", could not decode.");
        }

        return newDocument(id, expiry, decoded, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(final JsonLongDocument document) throws Exception {
        return Tuple.create(Unpooled.copiedBuffer(String.valueOf(document.content()), CharsetUtil.UTF_8),
            TranscoderUtils.LONG_COMPAT_FLAGS);
    }

    @Override
    public JsonLongDocument newDocument(String id, int expiry, Long content, long cas) {
        return JsonLongDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<JsonLongDocument> documentType() {
        return JsonLongDocument.class;
    }
}
