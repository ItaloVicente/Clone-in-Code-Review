        } else if (value == JsonValue.NULL) {
            addNull();
        } else if (checkType(value)) {
            content.add(value);
        } else {
            throw new IllegalArgumentException("Unsupported type for JsonArray: " + value.getClass());
