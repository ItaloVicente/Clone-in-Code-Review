    public List<JsonObject> errors() {
        return Blocking.blockForSingle(
                asyncQueryResult.errors().toList(), timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean finalSuccess() {
        return Blocking.blockForSingle(asyncQueryResult.finalSuccess().single(), timeout, TIMEOUT_UNIT);
