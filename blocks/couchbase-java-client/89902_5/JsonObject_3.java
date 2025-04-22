    @InterfaceStability.Uncommitted
    public JsonObject put(final String name, final Object value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(Object.class.getSimpleName(), encryptionProvider));
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
            String type = object.getString("type");
            String encrypted = object.getString("payload");
            EncryptionCrytoProvider provider = this.encryptionConfig.getCryptoProvider(alg);
            if (provider == null && provider.getKeyName().compareTo(key) != 0) {
                throw new Exception("Crypto provider mismatch");
            }
            decrypted = provider.decrypt(encrypted);
            String decryptedString = new String((byte[]) decrypted);
            if (type.compareTo(String.class.getSimpleName()) == 0) {
                return decryptedString;
            } else if (type.compareTo(Boolean.class.getSimpleName()) == 0) {
                return Boolean.parseBoolean(decryptedString);
            } else if (type.compareTo(Integer.class.getSimpleName()) == 0) {
                return Integer.parseInt(decryptedString);
            } else if (type.compareTo(Long.class.getSimpleName()) == 0) {
                return Long.parseLong(decryptedString);
            } else if (type.compareTo(Double.class.getSimpleName()) == 0) {
                return Double.parseDouble(decryptedString);
            } else if (type.compareTo(JsonObject.class.getSimpleName()) == 0) {
                return JsonObject.fromJson(decryptedString);
            } else if (type.compareTo(JsonArray.class.getSimpleName()) == 0) {
                return JsonArray.fromJson(decryptedString);
            } else if (type.compareTo(Map.class.getSimpleName()) == 0) {
                return JsonObject.fromJson(decryptedString).toString();
            } else if (type.compareTo(Number.class.getSimpleName()) == 0) {
                try {
                    return NumberFormat.getInstance().parse(decryptedString);
                } catch (Exception ex) {
                    return null;
                }
            } else if (type.compareTo(List.class.getSimpleName()) == 0) {
                return JsonArray.from(decryptedString).toList();
            } else if (type.compareTo("null") == 0) {
                return null;
            }
        } catch (Exception ex) {
        }
        return decrypted;
    }


