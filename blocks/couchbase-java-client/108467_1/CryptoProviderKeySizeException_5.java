
package com.couchbase.client.encryption.errors;

public class CryptoProviderEncryptFailedException extends Exception {

    public CryptoProviderEncryptFailedException() {
        super();
    }

    public CryptoProviderEncryptFailedException(String message) {
        super(message);
    }

    public CryptoProviderEncryptFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoProviderEncryptFailedException(Throwable cause) {
        super(cause);
    }
}
