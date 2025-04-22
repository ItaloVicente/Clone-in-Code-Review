    @InterfaceStability.Uncommitted
    public JsonObject put(String name, boolean value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(Boolean.class.getSimpleName(), encryptionProvider));
        content.put(name, String.valueOf(value));
        return this;
    }

