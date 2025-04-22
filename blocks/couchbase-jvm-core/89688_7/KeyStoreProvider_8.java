
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public interface KeyStoreProvider {

    @InterfaceAudience.Private
    byte[] getKey(String keyName) throws Exception;

    @InterfaceAudience.Public
    void storeKey(String keyName, byte[] secretKey) throws Exception;

    @InterfaceAudience.Public
    void storeKey(String keyName, byte[] publicKey, byte[] privateKey) throws Exception;
}
