
    @InterfaceStability.Uncommitted
    public JsonObject putNull(String name, String encryptionProvider) {
        this.encryptionPathInfo.put(name, encryptionProvider);
        content.put(name, null);
        return this;
    }

