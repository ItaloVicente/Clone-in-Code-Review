
package com.couchbase.client.encryption.errors;

public class CryptoProviderMissingSigningKeyException extends Exception {

    public CryptoProviderMissingSigningKeyException() {
        super();
    }

    public CryptoProviderMissingSigningKeyException(String message) {
        super(message);
    }

    public CryptoProviderMissingSigningKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoProviderMissingSigningKeyException(Throwable cause) {
        super(cause);
    }
}
