
    public static Expression arrayFlatten(Expression expression, long depth) {
        return x("ARRAY_FLATTEN(" + expression.toString() + ", " + depth + ")");
    }

    public static Expression arrayFlatten(String expression, long depth) {
        return arrayFlatten(x(expression), depth);
    }

    public static Expression arrayFlatten(JsonArray array, long depth) {
        return arrayFlatten(x(array), depth);
    }

