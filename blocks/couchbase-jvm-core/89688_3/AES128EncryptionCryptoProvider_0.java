
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.utils.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@InterfaceStability.Uncommitted
public class AES128EncryptionCryptoProvider implements EncryptionCrytoProvider {

    private KeyStoreProvider keyStoreProvider = null;
    private String keyName = null;

    @Override
    public KeyStoreProvider getkeyStoreProvider() {
        return this.keyStoreProvider;
    }

    @Override
    public void setkeyStoreProvider(KeyStoreProvider provider) {
        this.keyStoreProvider = provider;
    }

    @Override
    public String getKeyName() {
        return this.keyName;
    }

    @Override
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public Object encrypt(Object data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(this.keyStoreProvider.getKey(this.keyName), "AES");
        checkKeySize(key);

        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(((String) data).getBytes());
        byte[] encryptedWithIv = new byte[encrypted.length + ivSize];
        System.arraycopy(iv, 0, encryptedWithIv, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedWithIv, ivSize, encrypted.length);
        return Base64.encode(encryptedWithIv);
    }

    @Override
    public Object decrypt(Object encrypted) throws Exception {
        SecretKeySpec key = new SecretKeySpec(this.keyStoreProvider.getKey(this.keyName), "AES");
        checkKeySize(key);

        byte[] encryptedWithIv = Base64.decode((String) encrypted);

        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        System.arraycopy(encryptedWithIv, 0, iv, 0, ivSize);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        int encryptedSize = encryptedWithIv.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedWithIv, ivSize, encryptedBytes, 0, encryptedSize);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(encryptedBytes);
    }

    @Override
    public String getProviderName() {
        return "AES-128";
    }

    private void checkKeySize(SecretKeySpec key) throws Exception {
        int keySize = key.getEncoded().length;
        if (keySize != 16) {
            throw new Exception("Invalid key size " + keySize + " for AES128 Algorithm");
        }
    }
}
