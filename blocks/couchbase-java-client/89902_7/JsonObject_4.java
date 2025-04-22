    @InterfaceStability.Uncommitted
    public JsonObject put(final String name, final String value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, encryptionProvider);
        content.put(name, value);
        return this;
    }

