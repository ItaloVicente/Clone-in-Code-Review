
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceAudience;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@InterfaceAudience.Private
public class AESCryptoProviderBase implements CryptoProvider {

    private KeyStoreProvider keyStoreProvider = null;
    private String keyName = null;
    protected int keySize = 32;

    @Override
    public KeyStoreProvider getKeyStoreProvider() {
        return this.keyStoreProvider;
    }

    @Override
    public void setKeyStoreProvider(KeyStoreProvider provider) {
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

    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(this.keyStoreProvider.getKey(this.keyName), "AES");
        checkKeySize(key);

        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(data);
        byte[] encryptedWithIv = new byte[encrypted.length + ivSize];
        System.arraycopy(iv, 0, encryptedWithIv, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedWithIv, ivSize, encrypted.length);
        return encryptedWithIv;
    }

    @Override
    public byte[] decrypt(byte[] encryptedWithIv) throws Exception {
        SecretKeySpec key = new SecretKeySpec(this.keyStoreProvider.getKey(this.keyName), "AES");
        checkKeySize(key);

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
        return "AES";
    }

    private void checkKeySize(SecretKeySpec key) throws Exception {
        int keySize = key.getEncoded().length;
        if (keySize != this.keySize) {
            throw new Exception("Invalid key size " + keySize + " for "+ this.getProviderName() +" Algorithm");
        }
    }
}
