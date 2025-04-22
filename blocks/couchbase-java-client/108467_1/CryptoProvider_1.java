
package com.couchbase.client.encryption;

public interface CryptoProvider {

    KeyStoreProvider getKeyStoreProvider();

    void setKeyStoreProvider(KeyStoreProvider provider);

    byte[] encrypt(byte[] data) throws Exception;

    int getIVSize();

    byte[] decrypt(byte[] encrypted) throws Exception;

    byte[] getSignature(byte[] message) throws Exception;

    boolean verifySignature(byte[] message, byte[] signature) throws Exception;

    String getProviderAlgorithmName();

     @Deprecated
     String getProviderName();

    boolean checkAlgorithmNameMatch(String name);

    void setAlias(String alias);
}
