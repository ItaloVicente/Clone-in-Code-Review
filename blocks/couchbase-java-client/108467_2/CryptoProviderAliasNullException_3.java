
package com.couchbase.client.encryption.errors;

public class CryptoProviderAliasNullException extends Exception {

    public CryptoProviderAliasNullException() { super(); }

    public CryptoProviderAliasNullException(String message) { super(message); }

    public CryptoProviderAliasNullException(String message, Throwable cause) { super(message, cause); }

    public CryptoProviderAliasNullException(Throwable cause) { super(cause); }
}
