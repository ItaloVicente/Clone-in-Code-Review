        for (Map.Entry<String, ?> entry : mapData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == JsonValue.NULL) {
                value = null;
            }

            if (key == null) {
                throw new NullPointerException("The key is not allowed to be null");
            } else if (value instanceof Map) {
                try {
                    JsonObject sub = JsonObject.from((Map<String, ?>) value);
                    result.put(key, sub);
                } catch (ClassCastException e) {
                    throw e;
                } catch (Exception e) {
                    ClassCastException c = new ClassCastException("Couldn't convert sub-Map " + key + " to JsonObject");
                    c.initCause(e);
                    throw c;
                }
            } else if (value instanceof List) {
                try {
                    JsonArray sub = JsonArray.from((List<?>) value);
                    result.put(key, sub);
                } catch (Exception e) {
                    ClassCastException c = new ClassCastException("Couldn't convert sub-List " + key + " to JsonArray");
                    c.initCause(e);
                    throw c;
                }
            } else if (!checkType(value)) {
                throw new IllegalArgumentException("Unsupported type for JsonObject: " + value.getClass());
            } else {
                result.put(key, value);
