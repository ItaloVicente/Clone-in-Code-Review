    public JsonArray byteBufToJsonArray(ByteBuf input) throws Exception {
        byte[] inputBytes;
        if (input.hasArray()) {
            inputBytes = input.array();
        } else {
            inputBytes = new byte[input.readableBytes()];
            input.getBytes(0, inputBytes);
        }
        return JacksonTransformers.MAPPER.readValue(inputBytes, JsonArray.class);
    }

