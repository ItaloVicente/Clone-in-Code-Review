    public static Expression arrayDistinct(JsonArray array) {
        return arrayDistinct(x(array));
    }

    public static Expression arrayIfNull(Expression expression) {
        return x("ARRAY_IFNULL(" + expression.toString() + ")");
    }

    public static Expression arrayIfNull(String expression) {
        return arrayIfNull(x(expression));
    }

    public static Expression arrayIfNull(JsonArray array) {
        return arrayIfNull(x(array));
    }

    public static Expression arrayLength(Expression expression) {
        return x("ARRAY_LENGTH(" + expression.toString() + ")");
    }

    public static Expression arrayLength(String expression) {
        return arrayLength(x(expression));
    }

