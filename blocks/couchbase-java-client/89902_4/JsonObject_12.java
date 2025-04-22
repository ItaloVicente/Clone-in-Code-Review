    @InterfaceStability.Uncommitted
    public JsonObject put(String name, Number value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(Number.class.getSimpleName(), encryptionProvider));
        content.put(name, String.valueOf(value));
        return this;
    }

