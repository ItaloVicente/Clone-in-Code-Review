
package com.couchbase.client.encryption;

public interface KeyStoreProvider {

    byte[] getKey(String keyName) throws Exception;

    void storeKey(String keyName, byte[] key) throws Exception;

    String publicKeyName();

    void publicKeyName(String name);

    String privateKeyName();

    void privateKeyName(String name);

    String signingKeyName();

    void signingKeyName(String name);
}
