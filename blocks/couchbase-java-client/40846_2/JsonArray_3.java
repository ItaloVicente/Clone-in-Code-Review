    public JsonArray add(Object value) {
        if (checkType(value)) {
            content.add(value);
        } else {
            throw new IllegalArgumentException("Unsupported type for JsonArray: " + value.getClass());
        }
        return this;
    }

    public JsonArray add(String value) {
