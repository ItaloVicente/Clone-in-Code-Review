
package com.couchbase.client.core.encryption;

import org.junit.Assert;
import org.junit.Test;

public class HashicorpVaultKeyStoreProviderTest {

    @Test
    public void testKeyStore() throws Exception {
        HashicorpVaultKeyStoreProvider provider = new HashicorpVaultKeyStoreProvider("http://127.0.0.1:8200", "9767eb5d-3faa-f055-b58e-f86a8e376f94");
        String key = "00F3125AAD8E9972479D0129BEDED6BCF50492245F5B461D4EBF1B57D75C3BD9";
        provider.storeKey("testkey", key);
        Assert.assertEquals(key, provider.getKey("testkey"));
    }
}
