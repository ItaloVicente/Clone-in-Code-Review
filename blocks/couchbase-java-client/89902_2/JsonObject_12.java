    @InterfaceStability.Uncommitted
    public JsonObject put(String name, List<?> value, String encryptionProvider) {
        this.encryptionPathInfo.put(name + ":" + value.getClass().getSimpleName(), encryptionProvider);
        return put(name, JsonArray.from(value));
    }

