
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public class AES128CryptoProvider extends AESCryptoProviderBase {

    public AES128CryptoProvider() {
        this.keySize = 16;
    }

    @Override
    public String getProviderName() {
        return "AES-128";
    }
}
