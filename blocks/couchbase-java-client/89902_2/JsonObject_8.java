
        Map<String, String> paths = value.encryptionPathInfo();
        if (paths.size() > 0) {
            for (Map.Entry<String, String> entry: paths.entrySet()) {
                this.encryptionPathInfo.put(name + "/" + entry.getKey(), entry.getValue());
            }
            value.clearEncryptionPaths();
        }
        return this;
    }


    @InterfaceStability.Uncommitted
    public JsonObject put(String name, JsonObject value, String encryptionProvider) {
        this.encryptionPathInfo.put(name + ":" + JsonObject.class.getSimpleName(), encryptionProvider);
        if (this == value) {
            throw new IllegalArgumentException("Cannot put self");
        }
        content.put(name, value.toString());
