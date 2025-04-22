    @InterfaceStability.Uncommitted
    public JsonObject put(String name, int value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(Integer.class.getSimpleName(), encryptionProvider));
        content.put(name, String.valueOf(value));
        return this;
    }

