
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public interface CrytoProvider {

    KeyStoreProvider getKeyStoreProvider();

    void setKeyStoreProvider(KeyStoreProvider provider);

    String getKeyName();

    void setKeyName(String keyName);

    byte[] encrypt(byte[] data) throws Exception;

    byte[] decrypt(byte[] encrypted) throws Exception;

    String getProviderName();
}
