
    public static FromPath selectDistinctRaw(Expression expression) {
        return new DefaultSelectPath(null).selectDistinctRaw(expression);
    }

    public static FromPath selectDistinctRaw(String expression) {
        return new DefaultSelectPath(null).selectDistinctRaw(expression);
    }
