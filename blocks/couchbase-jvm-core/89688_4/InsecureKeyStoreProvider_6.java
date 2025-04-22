
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;
import java.util.HashMap;

@InterfaceStability.Uncommitted
public class InsecureKeyStoreProvider implements KeyStoreProvider {

    private HashMap<String, byte[]> keys = new HashMap<String, byte[]>();

    @Override
    public byte[] getKey(String keyName) {
        return keys.get(keyName);
    }

    @Override
    public void storeKey(String keyName, byte[] secretKey) {
        keys.put(keyName, secretKey);
    }

    @Override
    public void storeKey(String keyName, byte[] publicKey, byte[] privateKey) {
        keys.put(keyName + "_public", publicKey);
        keys.put(keyName + "_private", privateKey);
    }
}
