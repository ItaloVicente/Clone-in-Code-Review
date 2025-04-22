package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonDoubleDocument;
import com.couchbase.client.java.error.TranscodingException;

public class JsonDoubleTranscoder extends AbstractTranscoder<JsonDoubleDocument, Double> {

    @Override
    protected JsonDoubleDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags, ResponseStatus status)
        throws Exception {
        double decoded;

        if (TranscoderUtils.hasCommonFlags(flags) && flags == TranscoderUtils.JSON_COMMON_FLAGS) {
            String val = content.toString(CharsetUtil.UTF_8);
            decoded = JacksonTransformers.MAPPER.readValue(val, Double.class);
        } else if (flags == 6 << 8 || flags == 7 << 8) {
            long rv = 0;
            int readable = content.readableBytes();
            for (int i = 0; i < readable; i++) {
                byte b = content.readByte();
                rv = (rv << 8) | (b < 0 ? 256 + b : b);
            }
            if (flags == 6 << 8) {
                decoded = Float.intBitsToFloat((int) rv);
            } else {
                decoded = Double.longBitsToDouble(rv);
            }
        } else {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non " +
                "JsonDoubleDocument id " + id + ", could not decode.");
        }

        return newDocument(id, expiry, decoded, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(final JsonDoubleDocument document) throws Exception {
        return Tuple.create(Unpooled.copiedBuffer(JacksonTransformers.MAPPER.writeValueAsString(document.content())
                , CharsetUtil.UTF_8), TranscoderUtils.DOUBLE_COMPAT_FLAGS);
    }

    @Override
    public JsonDoubleDocument newDocument(String id, int expiry, Double content, long cas) {
        return JsonDoubleDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<JsonDoubleDocument> documentType() {
        return JsonDoubleDocument.class;
    }
}
