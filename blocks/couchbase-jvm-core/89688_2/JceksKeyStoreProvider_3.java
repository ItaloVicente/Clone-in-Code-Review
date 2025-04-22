
package com.couchbase.client.core.encryption;

import java.util.HashMap;

public class InsecureKeyStoreProvider implements KeyStoreProvider {

    private HashMap<String, byte[]> keys = new HashMap<String, byte[]>();

    @Override
    public byte[] getKey(String keyName) {
        return keys.get(keyName);
    }

    @Override
    public void storeKey(String keyName, byte[] key) {
        keys.put(keyName, key);
    }
}
