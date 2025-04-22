
    @InterfaceStability.Uncommitted
    public JsonObject putNull(String name, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo("null", encryptionProvider));
        content.put(name, "null");
        return this;
    }

