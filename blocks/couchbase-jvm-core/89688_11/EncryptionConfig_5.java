
package com.couchbase.client.core.encryption;

import java.util.HashMap;
import java.util.Map;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public class EncryptionConfig {

    private Map<String, CryptoProvider> cryptoProvider;

    public EncryptionConfig() {
        this.cryptoProvider = new HashMap<String, CryptoProvider>();
    }

    public void addCryptoProvider(CryptoProvider provider) {
        this.cryptoProvider.put(provider.getProviderName(), provider);
    }

    public CryptoProvider getCryptoProvider(String providerName) {
        return this.cryptoProvider.get(providerName);
    }
}
