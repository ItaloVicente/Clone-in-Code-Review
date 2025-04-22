
package com.couchbase.client.core.encryption;

public interface KeyStoreProvider {

    byte[] getKey(String keyName) throws Exception;

    void storeKey(String keyName, byte[] key) throws Exception;
}
