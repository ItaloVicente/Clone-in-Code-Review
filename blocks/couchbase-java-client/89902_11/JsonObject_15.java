    @InterfaceStability.Uncommitted
    public Map<String, Object> toDecryptedMap() {
        Map<String, Object> copy = new HashMap<String, Object>(content.size());
        for (Map.Entry<String, Object> entry : content.entrySet()) {
            Object content = entry.getValue();
            String key = entry.getKey();
           if (entry.getKey().startsWith(ENCRYPTION_PREFIX)) {
                content = decrypt((JsonObject) entry.getValue());
                key = key.replace(ENCRYPTION_PREFIX, "");
            }
            if (content instanceof JsonObject) {
               JsonObject value = (JsonObject)content;
               value.setEncryptionConfig(this.encryptionConfig);
               copy.put(key,  ((JsonObject) content).toDecryptedMap());
            } else if (content instanceof JsonArray) {
                copy.put(key, ((JsonArray) content).toList());
            } else {
                copy.put(key, content);
            }
        }
        return copy;
    }

