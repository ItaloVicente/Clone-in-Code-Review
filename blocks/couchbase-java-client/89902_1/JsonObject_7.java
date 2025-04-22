    @InterfaceStability.Uncommitted
    public JsonObject put(String name, boolean value, String encryptionProvider) {
        this.encryptionPathInfo.put(name + ":" + Boolean.class.getSimpleName(), encryptionProvider);
        content.put(name, String.valueOf(value));
        return this;
    }

