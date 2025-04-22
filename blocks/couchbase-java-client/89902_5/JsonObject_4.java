    @InterfaceStability.Uncommitted
    public JsonObject put(final String name, final String value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(String.class.getSimpleName() , encryptionProvider));
        content.put(name, value);
        return this;
    }

