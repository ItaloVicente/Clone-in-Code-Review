
package com.couchbase.client.core.encryption;

import java.util.HashMap;
import java.util.Map;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public class EncryptionConfig {

    private Map<String, CrytoProvider> crytoProvider;

    public EncryptionConfig() {
        this.crytoProvider = new HashMap<String, CrytoProvider>();
    }

    public void addCryptoProvider(CrytoProvider provider) {
        this.crytoProvider.put(provider.getProviderName(), provider);
    }

    public CrytoProvider getCryptoProvider(String providerName) {
        return this.crytoProvider.get(providerName);
    }
}
