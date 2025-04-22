    public static Expression trim(Expression expression) {
        return x("TRIM(" + expression.toString() + ")");
    }

    public static Expression trim(String expression) {
        return trim(x(expression));
    }
