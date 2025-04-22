
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.utils.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@InterfaceStability.Uncommitted
public class RSAEncryptionCryptoProvider implements EncryptionCrytoProvider {
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
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(this.keyStoreProvider.getKey(this.keyName + "_private"));
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(((String) data).getBytes());
        return Base64.encode(encryptedBytes);
    }

    @Override
    public Object decrypt(Object encrypted) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(this.keyStoreProvider.getKey(this.keyName + "_public"));
        RSAPublicKey publicKeyKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        byte[] encryptedBytes = Base64.decode((String) encrypted);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKeyKey);
        return cipher.doFinal(encryptedBytes);
    }

    @Override
    public String getProviderName() {
        return "RSA";
    }
}
