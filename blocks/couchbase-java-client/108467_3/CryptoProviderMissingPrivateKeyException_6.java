
package com.couchbase.client.encryption.errors;

public class CryptoProviderKeySizeException extends Exception {

    public CryptoProviderKeySizeException() {
        super();
    }

    public CryptoProviderKeySizeException(String message) {
        super(message);
    }

    public CryptoProviderKeySizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoProviderKeySizeException(Throwable cause) {
        super(cause);
    }
}
