
package com.couchbase.client.java.transcoder.subdoc;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.subdoc.MultiValue;

public abstract class AbstractFragmentTranscoder implements FragmentTranscoder {

    @Override
    public <T> T decode(ByteBuf encoded, Class<? extends T> clazz) throws TranscodingException {
        return this.decodeWithMessage(encoded, clazz, null);
    }

    @Override
    public <T> ByteBuf encode(T value) throws TranscodingException {
        return encodeWithMessage(value, null);
    }

    @Override
    public <T> ByteBuf encodeWithMessage(T value, String transcodingErrorMessage) throws TranscodingException {
        if (value instanceof MultiValue)
            return doEncodeMulti((MultiValue<?>) value, transcodingErrorMessage);
        return doEncodeSingle(value, transcodingErrorMessage);
    }

    protected abstract <T> ByteBuf doEncodeSingle(T value, String transcodingErrorMessage) throws TranscodingException;
    protected abstract ByteBuf doEncodeMulti(MultiValue<?> multiValue, String transcodingErrorMessage) throws TranscodingException;
}
