package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonBooleanDocument;
import com.couchbase.client.java.error.TranscodingException;

public class JsonBooleanTranscoder extends AbstractTranscoder<JsonBooleanDocument, Boolean> {

    @Override
    protected JsonBooleanDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags, ResponseStatus status)
        throws Exception {
        boolean decoded;

        if (TranscoderUtils.hasCommonFlags(flags) && flags == TranscoderUtils.JSON_COMMON_FLAGS) {
            String val = content.toString(CharsetUtil.UTF_8);
            decoded = val.equals("true");
        } else if (flags == 1 << 8) {
            char val = content.getChar(0);
            decoded = val == '1';
        } else {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non " +
                "JsonBooleanDocument id " + id + ", could not decode.");
        }
        return newDocument(id, expiry, decoded, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(final JsonBooleanDocument document) throws Exception {
        ByteBuf encoded = Unpooled.copiedBuffer(document.content() ? "true" : "false", CharsetUtil.UTF_8);
        return Tuple.create(encoded, TranscoderUtils.BOOLEAN_COMPAT_FLAGS);
    }

    @Override
    public JsonBooleanDocument newDocument(String id, int expiry, Boolean content, long cas) {
        return JsonBooleanDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<JsonBooleanDocument> documentType() {
        return JsonBooleanDocument.class;
    }
}
