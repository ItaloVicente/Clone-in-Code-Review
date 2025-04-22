
package com.couchbase.client.core.encryption;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Arrays;

@Ignore
public class HashicorpVaultKeyStoreProviderTest {

    @Test
    public void testSimpleKeyStore() throws Exception {
        HashicorpVaultKeyStoreProvider provider = new HashicorpVaultKeyStoreProvider("127.0.0.1:8200", "9767eb5d-3faa-f055-b58e-f86a8e376f94");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keyGen.init(128, random);
        SecretKey secretKey = keyGen.generateKey();
        String keyName = "testkey";
        provider.storeKey(keyName, secretKey.getEncoded());
        byte[] storedKey = provider.getKey(keyName);
        Assert.assertTrue(Arrays.equals(secretKey.getEncoded(), storedKey));
    }
}
