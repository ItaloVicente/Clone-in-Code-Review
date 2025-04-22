        int toSkip = input.forEachByte(new WhitespaceSkipper());
        if (toSkip > 0) {
            input.skipBytes(toSkip);
        }
        input.markReaderIndex();
        byte first = input.readByte();
        input.resetReaderIndex();

        switch (first) {
            case '{':
                return byteBufToJsonObject(input);
            case '[':
                return byteBufToClass(input, JsonArray.class);
        }

        Object value = byteBufToClass(input, Object.class);
        if (value instanceof Map) {
            LOGGER.warn("A JSON object could not be fast detected (first byte '" + (char) first + "')");
            return JsonObject.from((Map<String, ?>) value);
        } else if (value instanceof List) {
            LOGGER.warn("A JSON array could not be fast detected (first byte '" + (char) first + "')");
            return JsonArray.from((List<?>) value);
        } else {
            return value;
        }
