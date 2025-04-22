
package com.couchbase.client.encryption.errors;

public class CryptoProviderNotFoundException extends Exception {

    public CryptoProviderNotFoundException() {
        super();
    }

    public CryptoProviderNotFoundException(String message) {
        super(message);
    }

    public CryptoProviderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoProviderNotFoundException(Throwable cause) {
        super(cause);
    }
}
