
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.utils.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESEncryptionCryptoProvider implements EncryptionCrytoProvider {

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

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec key = new SecretKeySpec(this.keyStoreProvider.getKey(this.keyName).getBytes("UTF-8"), "AES");

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

        SecretKeySpec key = new SecretKeySpec(this.keyStoreProvider.getKey(this.keyName).getBytes("UTF-8"), "AES");
        byte[] encryptedWithIv = Base64.decode((String) encrypted);

        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        System.arraycopy(encryptedWithIv, 0, iv, 0, ivSize);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        int encryptedSize = encryptedWithIv.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedWithIv, ivSize, encryptedBytes, 0, encryptedSize);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(encryptedBytes);
    }
}
