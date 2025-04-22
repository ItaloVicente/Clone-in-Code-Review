
package com.couchbase.client.core.encryption;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Arrays;

public class JceksKeyStoreProviderTest {

    @Test
    public void testSimpleKeyStore() throws Exception {
        JceksKeyStoreProvider provider = new JceksKeyStoreProvider();
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keyGen.init(128, random);
        SecretKey secretKey = keyGen.generateKey();
        String keyName = "testkey";
        provider.storeKey(keyName, secretKey.getEncoded());
        byte[] secret = provider.getKey(keyName);
        AES128EncryptionCryptoProvider cryptoProvider = new AES128EncryptionCryptoProvider();
        cryptoProvider.setkeyStoreProvider(provider);
        cryptoProvider.setKeyName(keyName);
        Object encrypted = cryptoProvider.encrypt("test");
        String decrypted = new String((byte[]) cryptoProvider.decrypt(encrypted));
        Assert.assertTrue(Arrays.equals(secret, secretKey.getEncoded()));
        Assert.assertEquals(decrypted, "test");
    }
}
