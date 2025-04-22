
package com.couchbase.client.java.error;

public class CryptoProviderMissingPublicKeyException extends Exception {

    public CryptoProviderMissingPublicKeyException() {
        super();
    }

    public CryptoProviderMissingPublicKeyException(String message) {
        super(message);
    }

    public CryptoProviderMissingPublicKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoProviderMissingPublicKeyException(Throwable cause) {
        super(cause);
    }
}
