
    @InterfaceStability.Uncommitted
    public JsonObject put(String name, JsonArray value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, encryptionProvider);
        content.put(name, value);
        return this;
    }

