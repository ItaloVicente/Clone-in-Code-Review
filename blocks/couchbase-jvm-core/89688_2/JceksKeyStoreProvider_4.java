
package com.couchbase.client.core.encryption;
import javax.crypto.SecretKey;
import java.security.KeyStore;

public class JceksKeyStoreProvider implements KeyStoreProvider {

    private final KeyStore ks;
    private String secretKeyPassword;

    public JceksKeyStoreProvider() throws Exception {
        this(null);
    }

    public JceksKeyStoreProvider(String secretKeyPassword) throws Exception {
        this.ks = KeyStore.getInstance("JCEKS");
        this.ks.load(null, null);
        this.secretKeyPassword = secretKeyPassword;
    }

    private KeyStore.PasswordProtection getProtection(String keyName) {
        KeyStore.PasswordProtection protection;
        if (this.secretKeyPassword == null) {
            protection = new KeyStore.PasswordProtection(keyName.toCharArray());
        } else {
            protection = new KeyStore.PasswordProtection(this.secretKeyPassword.toCharArray());
        }
        return protection;
    }

    @Override
    public byte[] getKey(String keyName) throws Exception {
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) this.ks.getEntry(keyName, getProtection(keyName));
        return entry.getSecretKey().getEncoded();
    }

    @Override
    public void storeKey(String keyName, byte[] key) throws Exception {
        SimpleSecretKey secretKey = new SimpleSecretKey(key);
        this.ks.setEntry(keyName, new KeyStore.SecretKeyEntry(secretKey), getProtection(keyName));
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
