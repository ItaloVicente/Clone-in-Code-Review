package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonStringDocument;
import com.couchbase.client.java.error.TranscodingException;

public class JsonStringTranscoder extends AbstractTranscoder<JsonStringDocument, String> {

    @Override
    protected JsonStringDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags,
        ResponseStatus status) throws Exception {
        String decoded = content.toString(CharsetUtil.UTF_8);
        if (TranscoderUtils.hasCommonFlags(flags) && flags == TranscoderUtils.JSON_COMMON_FLAGS) {
            decoded = decoded.substring(1, decoded.length() - 1);
        } else if (flags == 0) {
            if (decoded.startsWith("\"") && decoded.endsWith("\"")) {
                decoded = decoded.substring(1, decoded.length() - 1);
            }
        } else {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non " +
                "JsonStringDocument id " + id + ", could not decode.");
        }

        return newDocument(id, expiry, decoded, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(JsonStringDocument document) throws Exception {
        String content = "\"" + document.content() + "\"";
        return Tuple.create(Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), TranscoderUtils.JSON_COMPAT_FLAGS);
    }

    @Override
    public JsonStringDocument newDocument(String id, int expiry, String content, long cas) {
        return JsonStringDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<JsonStringDocument> documentType() {
        return JsonStringDocument.class;
    }
}
