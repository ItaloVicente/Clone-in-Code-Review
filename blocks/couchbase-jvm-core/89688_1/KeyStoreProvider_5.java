
package com.couchbase.client.core.encryption;

public interface KeyStoreProvider {

    String getKey(String keyName) throws Exception;

    void storeKey(String keyName, String key) throws Exception;
}
