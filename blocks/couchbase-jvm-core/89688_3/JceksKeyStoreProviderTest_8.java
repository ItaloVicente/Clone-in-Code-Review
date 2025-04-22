
package com.couchbase.client.core.encryption;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class HashicorpVaultKeyStoreProviderTest {

    @Test
    public void testKeyStore() throws Exception {
        HashicorpVaultKeyStoreProvider provider = new HashicorpVaultKeyStoreProvider("http://127.0.0.1:8200", "9767eb5d-3faa-f055-b58e-f86a8e376f94");
        String hexKey = "185B30D37A77C1E207B9E6C7062E695D8E8DABCE44EA1F10E02232FB658876A1";
        byte[] key = new byte[hexKey.length() / 2];

        for (int i = 0; i < key.length; i++) {
            String str = hexKey.substring(i, i + 2);
            key[i] = ((byte) Integer.parseInt(str, 16));
        }
        provider.storeKey("testkey", key);
        byte[] storedKey = provider.getKey("testkey");
        Assert.assertTrue(Arrays.equals(key, storedKey));
    }
}
