
    @InterfaceStability.Uncommitted
    public JsonObject put(String name, double value, String encryptionProvider) {
        this.encryptionPathInfo.put(name + ":" + Double.class.getSimpleName(), encryptionProvider);
        content.put(name, String.valueOf(value));
        return this;
    }

