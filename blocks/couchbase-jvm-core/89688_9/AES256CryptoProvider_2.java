
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public class AES256CryptoProvider extends AESCryptoProviderBase {

    public AES256CryptoProvider() {
        this.keySize = 32;
    }

    @Override
    public String getProviderName() {
        return "AES-256";
    }
}
