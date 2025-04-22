
package com.couchbase.client.java.transcoder.crypto;

import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.encryption.CrytoProvider;
import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.core.utils.Base64;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.core.encryption.EncryptionConfig;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.transcoder.AbstractTranscoder;
import com.couchbase.client.java.transcoder.JacksonTransformers;
import com.couchbase.client.java.transcoder.TranscoderUtils;

@InterfaceStability.Uncommitted
public class JsonCryptoTranscoder extends AbstractTranscoder<JsonDocument, JsonObject> {

    private final EncryptionConfig encryptionConfig;

    public JsonCryptoTranscoder(EncryptionConfig config) {
        this.encryptionConfig = config;
    }

    private void addEncryption(JsonObject content) throws Exception {
        if (content == null || content.encryptionPathInfo().size() == 0) {
            return;
        }

        for (Map.Entry<String, String> entry:content.encryptionPathInfo().entrySet()) {
            String[] pathSplit = entry.getKey().split("/");
            JsonObject parent = content;
            String lastPointer = pathSplit[pathSplit.length-1];

            for (int i=0; i < pathSplit.length-1; i++) {
                parent = (JsonObject) parent.get(pathSplit[i]);
            }

            Object value = parent.get(lastPointer);
            JsonObject encrypted = JsonObject.create();
            CrytoProvider provider = this.encryptionConfig.getCryptoProvider(entry.getValue());
            if (provider == null) {
                throw new Exception("Encryption provider does not exist in the configuration");
            }

            String jsonValue = JacksonTransformers.MAPPER.writeValueAsString(value);

            encrypted.put("kid", provider.getKeyName());
            encrypted.put("alg", entry.getValue());
            encrypted.put("payload", Base64.encode(provider.encrypt(jsonValue.getBytes())));
            parent.removeKey(lastPointer);
            parent.put(JsonObject.ENCRYPTION_PREFIX + lastPointer, encrypted);
        }
        content.clearEncryptionPaths();
    }

    @Override
    public Class<JsonDocument> documentType() {
        return JsonDocument.class;
    }

    @Override
    protected Tuple2<ByteBuf, Integer> doEncode(final JsonDocument document) throws Exception {
        addEncryption(document.content());
        return Tuple.create(jsonObjectToByteBuf(document.content()), TranscoderUtils.JSON_COMPAT_FLAGS);
    }

    @Override
    protected JsonDocument doDecode(String id, ByteBuf content, long cas, int expiry, int flags, ResponseStatus status)
            throws Exception {
        if (!TranscoderUtils.hasJsonFlags(flags)) {
            throw new TranscodingException("Flags (0x" + Integer.toHexString(flags) + ") indicate non-JSON document for "
                    + "id " + id + ", could not decode.");
        }
        JsonObject jsonObject = byteBufToJsonObject(content);
        jsonObject.setEncryptionConfig(encryptionConfig);
        return newDocument(id, expiry, jsonObject, cas);
    }

    @Override
    public JsonDocument newDocument(String id, int expiry, JsonObject content, long cas) {
        JsonDocument document = JsonDocument.create(id, expiry, content, cas);
        document.content().setEncryptionConfig(this.encryptionConfig);
        return document;
    }

    @Override
    public JsonDocument newDocument(String id, int expiry, JsonObject content, long cas,
                                    MutationToken mutationToken) {
        return JsonDocument.create(id, expiry, content, cas, mutationToken);
    }

    public String jsonObjectToString(JsonObject input) throws Exception {
        return JacksonTransformers.MAPPER.writeValueAsString(input);
    }

    private ByteBuf jsonObjectToByteBuf(JsonObject input) throws Exception {
        return Unpooled.wrappedBuffer(JacksonTransformers.MAPPER.writeValueAsBytes(input));
    }

    public JsonObject stringToJsonObject(String input) throws Exception {
        return JacksonTransformers.MAPPER.readValue(input, JsonObject.class);
    }

    public JsonObject byteBufToJsonObject(ByteBuf input) throws Exception {
        return TranscoderUtils.byteBufToClass(input, JsonObject.class, JacksonTransformers.MAPPER);
    }

    public Object byteBufJsonValueToObject(ByteBuf input) throws Exception {
        return TranscoderUtils.byteBufToGenericObject(input, JacksonTransformers.MAPPER);
    }
}
