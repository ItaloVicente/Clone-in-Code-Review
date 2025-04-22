
package com.couchbase.client.java.transcoder.subdoc;

import java.io.IOException;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.transcoder.TranscoderUtils;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class JacksonSubdocumentTranscoder implements SubdocumentTranscoder {

    private final ObjectMapper mapper;

    public JacksonSubdocumentTranscoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T decode(ByteBuf encoded, Class<? extends T> clazz) throws TranscodingException {
        return this.decodeWithMessage(encoded, clazz, null);
    }

    @Override
    public <T> T decodeWithMessage(ByteBuf encoded, Class<? extends T> clazz, String transcodingErrorMessage) throws TranscodingException {
        try {
            if (Object.class.equals(clazz)) {
                return (T) TranscoderUtils.byteBufToGenericObject(encoded, mapper);
            } else {
                return TranscoderUtils.byteBufToClass(encoded, clazz, mapper);
            }
        } catch (IOException e) {
            throw new TranscodingException(transcodingErrorMessage, e);
        }
    }

    @Override
    public <T> ByteBuf encode(T value) throws TranscodingException {
        return encodeWithMessage(value, null);
    }

    @Override
    public <T> ByteBuf encodeWithMessage(T value, String transcodingErrorMessage) throws TranscodingException {
        try {
            return Unpooled.wrappedBuffer(mapper.writeValueAsBytes(value));
        } catch (JsonProcessingException e) {
            throw new TranscodingException(transcodingErrorMessage, e);
        }
    }
}
