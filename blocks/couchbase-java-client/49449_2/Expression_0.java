    public static Expression i(final String... identifiers) {
        return new Expression(wrapWith('`', identifiers));
    }

    public static Expression s(final String... strings) {
        return new Expression(wrapWith('"', strings));
    }

