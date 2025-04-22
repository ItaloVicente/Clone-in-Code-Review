        if (byteValue == null) {
            return null;
        } else if (value == null) {
            try {
                value = OBJECT_MAPPER.readValue(byteValue, JsonObject.class);
                return value;
            } catch (IOException e) {
                throw new TranscodingException("Error deserializing row value from bytes to JsonObject", e);
            }
        } else {
            return value;
        }
