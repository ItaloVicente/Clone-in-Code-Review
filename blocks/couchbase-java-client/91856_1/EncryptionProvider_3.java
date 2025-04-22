package com.couchbase.client.java.encryption;


import com.couchbase.client.crypto.AES128CryptoProvider;
import com.couchbase.client.crypto.AES256CryptoProvider;
import com.couchbase.client.crypto.AESCryptoProviderBase;
import com.couchbase.client.crypto.RSACryptoProvider;

public enum EncryptionProvider {
    AES128 {
        @Override
        public String toString() {
            return AES128CryptoProvider.NAME;
        }
    },
    AES256 {
        @Override
        public String toString() {
            return AES256CryptoProvider.NAME;
        }
    },
    RSA {
        @Override
        public String toString() {
            return RSACryptoProvider.NAME;
        }
    }

}
