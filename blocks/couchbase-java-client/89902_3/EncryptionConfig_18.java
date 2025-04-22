
package com.couchbase.client.java.encryption;

import java.util.HashMap;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.encryption.EncryptionCrytoProvider;

@InterfaceStability.Uncommitted
public class EncryptionConfig {

    private Map<String, EncryptionCrytoProvider> crytoProvider;

    public EncryptionConfig() {
        this.crytoProvider = new HashMap<String, EncryptionCrytoProvider>();
    }

    public void addCryptoProvider(EncryptionCrytoProvider provider) {
        this.crytoProvider.put(provider.getProviderName(), provider);
    }

    public EncryptionCrytoProvider getCryptoProvider(String providerName) {
        return this.crytoProvider.get(providerName);
    }
}
