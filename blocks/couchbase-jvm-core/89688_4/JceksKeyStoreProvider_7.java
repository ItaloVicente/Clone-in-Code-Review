
package com.couchbase.client.core.encryption;

import com.couchbase.client.core.annotations.InterfaceStability;
import javax.crypto.SecretKey;
import java.security.KeyStore;

@InterfaceStability.Uncommitted
public class JceksKeyStoreProvider implements KeyStoreProvider {

    private final KeyStore ks;
    private String keyPassword;

    public JceksKeyStoreProvider() throws Exception {
        this(null);
    }

    public JceksKeyStoreProvider(String keyPassword) throws Exception {
        this.ks = KeyStore.getInstance("JCEKS");
        this.ks.load(null, null);
        this.keyPassword = keyPassword;
    }

    private KeyStore.PasswordProtection getProtection(String keyName) {
        KeyStore.PasswordProtection protection;
        if (this.keyPassword == null) {
            protection = new KeyStore.PasswordProtection(keyName.toCharArray());
        } else {
            protection = new KeyStore.PasswordProtection(this.keyPassword.toCharArray());
        }
        return protection;
    }

    @Override
    public byte[] getKey(String keyName) throws Exception {
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) this.ks.getEntry(keyName, getProtection(keyName));
        return entry.getSecretKey().getEncoded();
    }

    @Override
    public void storeKey(String keyName, byte[] secretKey) throws Exception {
        SimpleSecretKey secretKeyEntry = new SimpleSecretKey(secretKey);
        this.ks.setEntry(keyName, new KeyStore.SecretKeyEntry(secretKeyEntry), getProtection(keyName));
    }

    public void storeKey(String keyName, byte[] publicKey, byte[] privateKey) throws Exception {
        SimpleSecretKey privateKeyEntry = new SimpleSecretKey(privateKey);
        this.ks.setEntry(keyName + "_private", new KeyStore.SecretKeyEntry(privateKeyEntry), getProtection(keyName + "_private"));
        SimpleSecretKey publicKeyEntry = new SimpleSecretKey(publicKey);
        this.ks.setEntry(keyName + "_public", new KeyStore.SecretKeyEntry(publicKeyEntry), getProtection(keyName + "_public"));
    }

    private static class SimpleSecretKey implements SecretKey {
        private final byte[] secret;

        public SimpleSecretKey(byte[] secret) {
            this.secret = secret;
        }

        @Override
        public String getAlgorithm() {
            return "UNKNOWN";
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return secret;
        }
    }
}
