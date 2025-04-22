    public static Expression trim(String expression, String characters) {
        return trim(x(expression), characters);
    }

    public static Expression upper(Expression expression) {
        return x("UPPER(" + expression.toString() + ")");
    }

    public static Expression upper(String expression) {
        return upper(x(expression));
    }
