    public JsonObject byteBufToJsonObject(ByteBuf input) throws Exception {
        return byteBufToClass(input, JsonObject.class);
    }

    public Object byteBufJsonValueToObject(ByteBuf input) throws Exception {
        Object value = byteBufToClass(input, Object.class);
        if (value instanceof Map) {
            return JsonObject.from((Map<String, ?>) value);
        } else if (value instanceof List) {
            return JsonArray.from((List<?>) value);
        } else {
            return value;
        }
    }
