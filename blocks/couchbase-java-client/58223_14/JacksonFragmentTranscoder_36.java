
package com.couchbase.client.java.transcoder.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.subdoc.DocumentFragment;
import com.couchbase.client.java.error.TranscodingException;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface FragmentTranscoder {

    <T> T decode(ByteBuf encoded, Class<? extends T> clazz) throws TranscodingException;

    <T> T decodeWithMessage(ByteBuf encoded, Class<? extends T> clazz, String transcodingErrorMessage) throws TranscodingException;

    <T> ByteBuf encode(T value) throws TranscodingException;

    <T> ByteBuf encodeWithMessage(T value, String transcodingErrorMessage) throws TranscodingException;
}
