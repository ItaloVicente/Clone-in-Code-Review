
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.utils.Base64;
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
        AES128CryptoProvider cryptoProvider = new AES128CryptoProvider();
        cryptoProvider.setKeyStoreProvider(provider);
        cryptoProvider.setKeyName(keyName);
        String encrypted = Base64.encode(cryptoProvider.encrypt("test".getBytes()));
        String decrypted = new String(cryptoProvider.decrypt(Base64.decode(encrypted)));
        Assert.assertTrue(Arrays.equals(secret, secretKey.getEncoded()));
        Assert.assertEquals(decrypted, "test");
    }
}
