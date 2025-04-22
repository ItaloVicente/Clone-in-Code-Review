    @InterfaceStability.Uncommitted
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

