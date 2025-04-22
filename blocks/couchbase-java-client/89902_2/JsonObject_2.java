    @InterfaceStability.Uncommitted
    public JsonObject put(final String name, final Object value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, encryptionProvider);
        if (this == value) {
            throw new IllegalArgumentException("Cannot put self");
        } else if (value == JsonValue.NULL) {
            putNull(name);
        } else if (checkType(value)) {
            content.put(name, value);
        } else {
            throw new IllegalArgumentException("Unsupported type for JsonObject: " + value.getClass());
        }
        return this;
    }

    @InterfaceStability.Uncommitted
    private Object decrypt(JsonObject object) {
        Object decrypted = null;

        try {
            String key = object.getString("kid");
            String alg = object.getString("alg");
            String encrypted = object.getString("payload");
            EncryptionCrytoProvider provider = this.encryptionConfig.getCryptoProvider(alg);
            if (provider == null && provider.getKeyName().compareTo(key) != 0) {
                throw new Exception("Crypto provider mismatch");
            }
            decrypted = provider.decrypt(encrypted);
        } catch (Exception ex) { }
        return decrypted;
    }


