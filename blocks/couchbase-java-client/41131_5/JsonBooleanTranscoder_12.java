package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.error.TranscodingException;

public class JsonArrayTranscoder extends AbstractTranscoder<JsonArrayDocument, JsonArray> {

    @Override
    public Class<JsonArrayDocument> documentType() {
        return JsonArrayDocument.class;
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(final JsonArrayDocument document) throws Exception {
        String content = jsonArrayToString(document.content());
        return Tuple.create(Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), TranscoderUtils.JSON_COMMON_FLAGS);
    }

    @Override
    protected JsonArrayDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags, ResponseStatus status)
        throws Exception {
        if (!TranscoderUtils.hasJsonFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-JSON array document for "
                + "id " + id + ", could not decode.");
        }

        JsonArray converted = stringToJsonArray(content.toString(CharsetUtil.UTF_8));
        content.release();
        return newDocument(id, expiry, converted, cas);
    }

    @Override
    public JsonArrayDocument newDocument(String id, int expiry, JsonArray content, long cas) {
        return JsonArrayDocument.create(id, expiry, content, cas);
    }

    public String jsonArrayToString(JsonArray input) throws Exception {
        return JacksonTransformers.MAPPER.writeValueAsString(input);
    }

    public JsonArray stringToJsonArray(String input) throws Exception {
        return JacksonTransformers.MAPPER.readValue(input, JsonArray.class);
    }

}
