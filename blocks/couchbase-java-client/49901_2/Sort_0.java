    public static Sort desc(final String expression) {
        return desc(x(expression));
    }

    public static Sort asc(final Expression expression) {
        return new Sort(expression, Order.ASC);
    }

    public static Sort asc(final String expression) {
        return asc(x(expression));
    }
