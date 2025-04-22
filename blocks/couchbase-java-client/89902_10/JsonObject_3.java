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
            CryptoProvider provider = this.encryptionConfig.getCryptoProvider(alg);
            if (provider == null && provider.getKeyName().contentEquals(key)) {
                throw new Exception("Crypto provider mismatch");
            }

            byte[] decryptedBytes = provider.decrypt(Base64.decode(encrypted));
            String decryptedString = new String(decryptedBytes, Charset.forName("UTF-8"));
            decrypted = JacksonTransformers.MAPPER.readValue(decryptedString, Object.class);
            if (decrypted instanceof Map) {
                decrypted = JsonObject.from((Map<String, ?>)decrypted);
            } else if (decrypted instanceof List) {
                decrypted = JsonArray.from((List<?>)decrypted);
            }
            return decrypted;
        } catch (Exception ex) {
        }
        return decrypted;
    }


