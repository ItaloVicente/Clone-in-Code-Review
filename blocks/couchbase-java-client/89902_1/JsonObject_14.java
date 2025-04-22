    public Map<String, Object> toMap(boolean decrypt) {
        Map<String, Object> copy = new HashMap<String, Object>(content.size());
        for (Map.Entry<String, Object> entry : content.entrySet()) {
            Object content = entry.getValue();
            if (decrypt && entry.getKey().startsWith(ENCRYPTION_PREFIX)) {
                content = decrypt((JsonObject) entry.getValue());
            }
            if (content instanceof JsonObject) {
                copy.put(entry.getKey(), ((JsonObject) content).toMap());
            } else if (content instanceof JsonArray) {
                copy.put(entry.getKey(), ((JsonArray) content).toList());
            } else {
                copy.put(entry.getKey(), content);
            }
        }
        return copy;
    }

