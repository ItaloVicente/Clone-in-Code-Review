    public Map<String, String> encryptionPathInfo() {
        return this.encryptionPathInfo;
    }

    public void clearEncryptionPaths() {
        this.encryptionPathInfo.clear();
    }

    public void setEncryptionConfig(EncryptionConfig config) {
        this.encryptionConfig = config;
    }

    private void recursiveDecrypt(JsonObject jsonObject) {
        for (Map.Entry<String, Object> entry : jsonObject.content.entrySet()) {
            if (entry.getKey().startsWith(ENCRYPTION_PREFIX)) {
                JsonObject value = (JsonObject) entry.getValue();
                jsonObject.removeKey(entry.getKey());
                String key = entry.getKey().replaceAll(ENCRYPTION_PREFIX, "");
                String type = value.getString("type");
                String decryptedString = new String((byte[])decrypt(value));

                if (decryptedString.length() == 0) {
                    jsonObject.putNull(key);
                }

                if (type.compareTo(String.class.getSimpleName()) == 0) {
                    jsonObject.put(key, decryptedString);
                } else if (type.compareTo(Integer.class.getSimpleName()) == 0) {
                    jsonObject.put(key, Integer.parseInt(decryptedString));
                } else if (type.compareTo(Long.class.getSimpleName()) == 0) {
                    jsonObject.put(key, Long.parseLong(decryptedString));
                } else if (type.compareTo(Double.class.getSimpleName()) == 0) {
                    jsonObject.put(key, Double.parseDouble(decryptedString));
                } else if (type.compareTo(JsonObject.class.getSimpleName()) == 0) {
                    jsonObject.put(key, JsonObject.fromJson(decryptedString));
                } else if (type.compareTo(JsonArray.class.getSimpleName()) == 0) {
                    jsonObject.put(key, JsonArray.fromJson(decryptedString));
                } else if (type.compareTo(Map.class.getSimpleName()) == 0) {
                    jsonObject.put(key, JsonObject.fromJson(decryptedString).toString());
                } else if (type.compareTo(Number.class.getSimpleName()) == 0) {
                    try {
                        jsonObject.put(key, NumberFormat.getInstance().parse(decryptedString));
                    } catch (Exception ex) {
                        jsonObject.putNull(key);
                    }
                } else if (type.compareTo(List.class.getSimpleName()) == 0) {
                    jsonObject.put(key, JsonArray.from(decryptedString).toList());
                } else if (type.compareTo("null") == 0) {
                    jsonObject.putNull(key);
                }
            } else if (entry.getValue() instanceof JsonObject) {
                recursiveDecrypt((JsonObject) entry.getValue());
            }
        }
    }

    public void decryptAllValues() {
        recursiveDecrypt(this);
    }

    public String toString(boolean decrypt) {
        try {
            if (decrypt) {
                decryptAllValues();
            }
            return JacksonTransformers.MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot convert JsonObject to Json String", e);
        }
    }

