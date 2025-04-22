
package com.couchbase.client.encryption;

import com.couchbase.client.encryption.errors.CryptoProviderAliasNullException;
import com.couchbase.client.encryption.errors.CryptoProviderMissingPublicKeyException;
import com.couchbase.client.encryption.errors.CryptoProviderNotFoundException;
import com.couchbase.client.encryption.errors.CryptoProviderSigningFailedException;

import java.util.HashMap;
import java.util.Map;

public class CryptoManager {

    private Map<String, CryptoProvider> cryptoProviderMap;

    public CryptoManager() {
        this.cryptoProviderMap = new HashMap<String, CryptoProvider>();
    }

    public void registerProvider(String name, CryptoProvider provider) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new CryptoProviderAliasNullException("Cryptographic providers require a non-null, empty alias be configured.");
        }
        this.cryptoProviderMap.put(name, provider);
        provider.setAlias(name);
    }

    public CryptoProvider getProvider(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new CryptoProviderAliasNullException("Cryptographic providers require a non-null, empty alias be configured.");
        }
        if (!this.cryptoProviderMap.containsKey(name) || this.cryptoProviderMap.get(name) == null) {
            throw new CryptoProviderNotFoundException("The cryptographic provider could not be found for the alias: " + name);
        }
        return this.cryptoProviderMap.get(name);
  }

    public void throwMissingPublicKeyEx(String alias) throws Exception {
        throw new CryptoProviderMissingPublicKeyException("Cryptographic providers require a non-null, empty public and key identifier (kid) be configured for the alias: " + alias);
    }

    public void throwSigningFailedEx(String alias) throws Exception {
        throw new CryptoProviderSigningFailedException("The authentication failed while checking the signature of the message payload for the alias: " + alias);
    }
}
