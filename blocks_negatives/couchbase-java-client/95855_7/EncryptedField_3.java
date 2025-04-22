    EncryptionProvider provider();

    /**
     * Encryption key name to be used, else the default key set on the provider will be used.
     *
     * @return encryption key name
     */
    String key() default "";

    /**
     * HMAC key name to be used, else the default HMAC key set on the provider will be used.
     *
     * @return HMAC key name
     */
    String hmac() default "";
}
