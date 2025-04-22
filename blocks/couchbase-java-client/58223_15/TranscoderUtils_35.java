    public static <T> T byteBufToClass(ByteBuf input, Class<? extends T> clazz, ObjectMapper mapper) throws IOException {
        byte[] inputBytes;
        int offset = 0;
        int length = input.readableBytes();
        if (input.hasArray()) {
            inputBytes = input.array();
            offset = input.arrayOffset() + input.readerIndex();
        } else {
            inputBytes = new byte[length];
            input.getBytes(input.readerIndex(), inputBytes);
        }
        return mapper.readValue(inputBytes, offset, length, clazz);
    }

    public static Object byteBufToGenericObject(ByteBuf input, ObjectMapper mapper) throws IOException {
        int toSkip = input.forEachByte(new WhitespaceSkipper());
        if (toSkip > 0) {
            input.skipBytes(toSkip);
        }
        input.markReaderIndex();
        byte first = input.readByte();
        input.resetReaderIndex();

        switch (first) {
            case '{':
                return byteBufToClass(input, JsonObject.class, mapper);
            case '[':
                return byteBufToClass(input, JsonArray.class, mapper);
        }

        Object value = byteBufToClass(input, Object.class, mapper);
        if (value instanceof Map) {
            LOGGER.warn("A JSON object could not be fast detected (first byte '" + (char) first + "')");
            return JsonObject.from((Map<String, ?>) value);
        } else if (value instanceof List) {
            LOGGER.warn("A JSON array could not be fast detected (first byte '" + (char) first + "')");
            return JsonArray.from((List<?>) value);
        } else {
            return value;
        }
    }

