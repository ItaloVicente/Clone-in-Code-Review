        return asyncViewRow.value();
    }

    @Override
    public JsonDocument document() {
        return document(timeout, TIMEOUT_UNIT);
