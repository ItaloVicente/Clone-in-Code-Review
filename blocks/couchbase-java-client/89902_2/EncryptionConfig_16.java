
package com.couchbase.client.java.encryption;

import java.util.HashMap;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.encryption.AES256EncryptionCryptoProvider;
import com.couchbase.client.core.encryption.EncryptionCrytoProvider;


@InterfaceStability.Uncommitted
public class EncryptionConfig {

    private Map<String, EncryptionCrytoProvider> crytoProvider;

    public EncryptionConfig() {
        crytoProvider = new HashMap<String, EncryptionCrytoProvider>();
    }

    public void addCryptoProvider(EncryptionCrytoProvider provider) {
        if (provider instanceof AES256EncryptionCryptoProvider) {
            crytoProvider.put("AES-256", provider);
        }
    }

    public void addCryptoProvider(String providerName, EncryptionCrytoProvider provider) {
        crytoProvider.put(providerName, provider);
    }


    public EncryptionCrytoProvider getCryptoProvider(String providerName) {
        return crytoProvider.get(providerName);
    }
}
