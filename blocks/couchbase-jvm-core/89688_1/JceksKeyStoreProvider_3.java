
package com.couchbase.client.core.encryption;

import java.util.HashMap;

public class InsecureKeyStoreProvider implements KeyStoreProvider {

    private HashMap<String, String> keys = new HashMap<String, String>();

    @Override
    public String getKey(String keyName) {
        return keys.get(keyName);
    }

    @Override
    public void storeKey(String keyName, String key) {
        keys.put(keyName, key);
    }
}
