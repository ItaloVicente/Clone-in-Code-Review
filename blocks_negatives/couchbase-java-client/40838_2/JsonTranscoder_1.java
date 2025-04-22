    /**
     *
     */
    static class JsonObjectDeserializer extends JsonDeserializer<JsonObject> {
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

        private JsonObject decodeObject(final JsonParser parser, final JsonObject target) throws IOException {
