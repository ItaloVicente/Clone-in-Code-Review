
package com.couchbase.client.encryption.errors;

public class CryptoProviderDecryptFailedException extends Exception {

    public CryptoProviderDecryptFailedException() { super(); }

    public CryptoProviderDecryptFailedException(String message) { super(message); }

    public CryptoProviderDecryptFailedException(String message, Throwable cause) { super(message, cause); }

    public CryptoProviderDecryptFailedException(Throwable cause) { super(cause); }
}
