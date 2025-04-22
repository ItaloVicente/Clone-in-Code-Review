
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
public interface KeyStoreProvider {

    byte[] getKey(String keyName) throws Exception;

    void storeKey(String keyName, byte[] secretKey) throws Exception;

    void storeKey(String keyName, byte[] publicKey, byte[] privateKey) throws Exception;
}
