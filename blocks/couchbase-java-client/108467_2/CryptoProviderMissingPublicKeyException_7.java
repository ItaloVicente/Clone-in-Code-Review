
package com.couchbase.client.encryption.errors;

public class CryptoProviderMissingPrivateKeyException extends Exception {

    public CryptoProviderMissingPrivateKeyException() {
        super();
    }

    public CryptoProviderMissingPrivateKeyException(String message) {
        super(message);
    }

    public CryptoProviderMissingPrivateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoProviderMissingPrivateKeyException(Throwable cause) {
        super(cause);
    }
}
