
    public static PrepareStatement prepare(String statement) {
        if (statement == null) {
            throw new NullPointerException("Statement to prepare cannot be null");
        }
        if (statement.startsWith(PREPARE_PREFIX)) {
            statement = statement.replaceFirst(PREPARE_PREFIX, "");
        }
        Statement toPrepare = new Query.RawStatement(statement);
        return new PrepareStatement(toPrepare);
    }
