    @Override
    public LetPath onKeysValues(String... constantKeys) {
        if (constantKeys.length == 1) {
            return onKeys(s(constantKeys[0]));
        } else {
            return onKeys(JsonArray.from(constantKeys));
        }
    }

