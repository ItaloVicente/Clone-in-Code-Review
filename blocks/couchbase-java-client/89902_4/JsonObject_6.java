    @InterfaceStability.Uncommitted
    public JsonObject put(String name, long value, String encryptionProvider) {
        this.encryptionPathInfo.put(name, new EncryptionInfo(Long.class.getSimpleName(), encryptionProvider));
        content.put(name, String.valueOf(value));
        return this;
    }

