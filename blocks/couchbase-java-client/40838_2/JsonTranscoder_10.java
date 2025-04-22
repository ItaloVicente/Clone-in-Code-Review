    static class JsonObjectDeserializer extends AbstractJsonValueDeserializer<JsonObject> {
        @Override
        public JsonObject deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException {
            if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
                return decodeObject(jp, JsonObject.empty());
            } else {
                throw new IllegalStateException("Expecting Object as root level object, " +
                    "was: " + jp.getCurrentToken());
            }
        }
    }

    static class JsonArrayDeserializer extends AbstractJsonValueDeserializer<JsonArray> {
        @Override
        public JsonArray deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException {
            if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
                return decodeArray(jp, JsonArray.empty());
            } else {
                throw new IllegalStateException("Expecting Array as root level object, " +
                    "was: " + jp.getCurrentToken());
            }
        }
