package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.document.SerializableDocument;
import com.couchbase.client.java.error.TranscodingException;

import java.io.Serializable;

public class SerializableTranscoder extends AbstractTranscoder<SerializableDocument, Serializable> {

    @Override
    protected SerializableDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags,
        ResponseStatus status) throws Exception {
        if (!TranscoderUtils.hasSerializableFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-serialized " +
                "document for id " + id + ", could not decode.");
        }

        Serializable deserialized = TranscoderUtils.deserialize(content);
        return newDocument(id, expiry, deserialized, cas);
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(final SerializableDocument document) throws Exception {
        return Tuple.create(TranscoderUtils.serialize(document.content()), TranscoderUtils.SERIALIZED_COMPAT_FLAGS);
    }

    @Override
    public SerializableDocument newDocument(String id, int expiry, Serializable content, long cas) {
        return SerializableDocument.create(id, expiry, content, cas);
    }

    @Override
    public Class<SerializableDocument> documentType() {
        return SerializableDocument.class;
    }
}
