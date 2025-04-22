    @InterfaceStability.Uncommitted
    public String toDecryptedString() {
        try {
            Map<String,Object> mapData = this.toDecryptedMap();
            return JacksonTransformers.MAPPER.writeValueAsString(JsonObject.from(mapData));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot convert JsonObject to Json String", e);
        }
    }

