
    @Override
    public LetPath useKeys(Expression expression) {
        element(new KeysElement(KeysElement.ClauseType.USE_KEYSPACE, expression));
        return new DefaultLetPath(this);
    }

    @Override
    public LetPath useKeys(String... keys) {
        if (keys.length == 1) {
            return useKeys(Expression.s(keys[0]));
        }
        return useKeys(JsonArray.from(keys));
    }

    @Override
    public LetPath useKeys(JsonArray keys) {
        return useKeys(x(keys));
    }
