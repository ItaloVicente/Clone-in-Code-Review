    public static Expression arrayAppend(String expression, Expression value) {
        return arrayAppend(x(expression), value);
    }

    public static Expression arrayAppend(JsonArray array, Expression value) {
        return arrayAppend(x(array), value);
    }

    public static Expression arrayAvg(Expression expression) {
        return x("ARRAY_AVG(" + expression.toString() + ")");
    }

    public static Expression arrayAvg(String expression) {
        return arrayAvg(x(expression));
    }

    public static Expression arrayAvg(JsonArray array) {
        return arrayAvg(x(array));
    }

    public static Expression arrayConcat(Expression expression1, Expression expression2) {
        return x("ARRAY_CONCAT(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression arrayConcat(String expression1, String expression2) {
        return arrayConcat(x(expression1), x(expression2));
    }

    public static Expression arrayConcat(JsonArray array1, JsonArray array2) {
        return arrayConcat(x(array1), x(array2));
    }

    public static Expression arrayContains(Expression expression, Expression value) {
        return x("ARRAY_CONTAINS(" + expression.toString() + ", " + value.toString() + ")");
    }

    public static Expression arrayContains(String expression, Expression value) {
        return arrayContains(x(expression), value);
    }

    public static Expression arrayContains(JsonArray array, Expression value) {
        return arrayContains(x(array), value);
    }

    public static Expression arrayCount(Expression expression) {
        return x("ARRAY_COUNT(" + expression.toString() + ")");
    }

    public static Expression arrayCount(String expression) {
        return arrayCount(x(expression));
    }

    public static Expression arrayCount(JsonArray array) {
        return arrayCount(x(array));
    }

    public static Expression arrayDistinct(Expression expression) {
        return x("ARRAY_DISTINCT(" + expression.toString() + ")");
    }

    public static Expression arrayDistinct(String expression) {
        return arrayDistinct(x(expression));
