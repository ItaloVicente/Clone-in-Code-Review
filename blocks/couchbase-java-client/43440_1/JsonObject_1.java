    public static JsonObject from(Map<String, ?> mapData) {
        if (mapData == null || mapData.isEmpty()) {
            return JsonObject.empty();
        }

        JsonObject value = new JsonObject();
        for (Map.Entry<String, ?> entry : mapData.entrySet()) {
            if (entry.getKey() == null) {
                throw new NullPointerException("The null key is not supported");
            } else if (entry.getValue() == null) {
                throw new NullPointerException("Unsupported null value for key " + entry.getKey());
            } else if (!checkType(entry.getValue())) {
                throw new IllegalArgumentException("Unsupported type for JsonObject: " + entry.getValue().getClass());
            }
            value.put(entry.getKey(), entry.getValue());
        }
        return value;
    }

