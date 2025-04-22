    @SuppressWarnings("unchecked")
    static Object coerce(Object value) {
        if (checkType(value)) {
            return value;
        }
        if (value instanceof Map) {
            return JsonObject.from((Map) value);
        }
        if (value instanceof List) {
            return JsonArray.from((List) value);
        }
        if (value instanceof JsonNull) {
            return null;
        }
        throw new IllegalArgumentException("Unsupported type for JSON value: " + value.getClass());
    }
