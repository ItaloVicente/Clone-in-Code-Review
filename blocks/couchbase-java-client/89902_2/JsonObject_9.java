    @InterfaceStability.Uncommitted
    public JsonObject put(String name, Map<String, ?> value, String encryptionProvider) {
        this.encryptionPathInfo.put(name + ":" + Map.class.getSimpleName(), encryptionProvider);
        return put(name, JsonObject.from(value));
    }

