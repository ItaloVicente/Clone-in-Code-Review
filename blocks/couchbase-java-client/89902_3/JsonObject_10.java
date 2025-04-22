
    @InterfaceStability.Uncommitted
    public JsonObject put(String name, JsonArray value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(JsonArray.class.getSimpleName(), encryptionProvider));
        content.put(name, value.toString());
        return this;
    }

