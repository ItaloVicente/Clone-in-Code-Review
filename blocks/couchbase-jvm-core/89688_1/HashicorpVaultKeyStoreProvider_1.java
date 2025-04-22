
package com.couchbase.client.core.encryption;


public interface EncryptionCrytoProvider {

    KeyStoreProvider getkeyStoreProvider();

    void setkeyStoreProvider(KeyStoreProvider provider);

    String getKeyName();

    void setKeyName(String keyName);

    Object encrypt(Object data) throws Exception;

    Object decrypt(Object encrypted) throws Exception;
}
