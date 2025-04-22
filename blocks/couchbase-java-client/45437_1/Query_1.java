    public static SimpleQuery raw(String statement) {
        return simple(new RawStatement(statement));
    }

    public static SimpleQuery raw(String statement, QueryParams params) {
        return simple(new RawStatement(statement), params);
    }

