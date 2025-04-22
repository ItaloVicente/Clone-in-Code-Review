package com.couchbase.client.java.transcoder.subdoc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.ByteBufOutputStream;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.subdoc.MultiValue;
import com.couchbase.client.java.transcoder.TranscoderUtils;

public abstract class AbstractByteArrayFragmentTranscoder extends AbstractFragmentTranscoder {

    @Override
    public <T> T decodeWithMessage(ByteBuf encoded, Class<? extends T> clazz, String transcodingErrorMessage) throws TranscodingException {
        try {
            TranscoderUtils.ByteBufToArray toArray = TranscoderUtils.byteBufToByteArray(encoded);
            if (Object.class.equals(clazz)) {
                return (T) byteArrayToGenericObject(toArray.byteArray, toArray.offset, toArray.length);
            } else {
                return byteArrayToClass(toArray.byteArray, toArray.offset, toArray.length, clazz);
            }
        } catch (Exception e) {
            throw new TranscodingException(transcodingErrorMessage, e);
        }
    }

    @Override
    protected <T> ByteBuf doEncodeSingle(T value, String transcodingErrorMessage) throws TranscodingException {
        try {
            return Unpooled.wrappedBuffer(writeValueAsBytes(value));
        } catch (Exception e) {
            throw new TranscodingException(transcodingErrorMessage, e);
        }
    }

    @Override
    protected ByteBuf doEncodeMulti(MultiValue<?> multiValue, String transcodingErrorMessage) throws TranscodingException {
        final ByteBufOutputStream out = new ByteBufOutputStream(Unpooled.buffer(4 * multiValue.size()));
        try {
            for (Iterator<?> iterator = multiValue.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                writeValueIntoStream(out, o);
                if (iterator.hasNext()) {
                   out.writeBytes(",");
                }
            }
            return out.buffer();
        } catch (Exception e) {
            throw new TranscodingException(transcodingErrorMessage, e);
        }
    }

    protected abstract Object byteArrayToGenericObject(byte[] byteArray, int offset, int length) throws IOException;

    protected abstract <T> T byteArrayToClass(byte[] byteArray, int offset, int length, Class<? extends T> clazz) throws IOException;

    protected abstract <T> byte[] writeValueAsBytes(T value) throws IOException;

    protected abstract void writeValueIntoStream(OutputStream out, Object o) throws IOException;
}
