package com.couchbase.client.java.document.json;

import java.util.HashMap;
import java.util.Map;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.encryption.EncryptionProvider;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class ValueEncryptionConfig {
    private EncryptionProvider provider;
    private Map<String, String> keys = new HashMap<String, String>();

    @InterfaceAudience.Public
    public ValueEncryptionConfig(EncryptionProvider provider) {
        this.provider = provider;
    }

    @InterfaceAudience.Public
    public void addKey(String keyName) {
        this.keys.put("encryption", keyName);
    }

    @InterfaceAudience.Public
    public void addHMACKey(String keyName) {
        this.keys.put("hmac", keyName);
    }

    @InterfaceAudience.Public
    public EncryptionProvider getProvider() {
        return this.provider;
    }

    @InterfaceAudience.Public
    public String getEncryptionKey() {
        return keys.get("encryption");
    }

    @InterfaceAudience.Public
    public String getHMACKey() {
        return keys.get("hmac");
    }
}
