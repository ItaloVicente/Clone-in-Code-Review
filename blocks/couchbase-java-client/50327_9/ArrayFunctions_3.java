
    public static Expression arrayMax(Expression expression) {
        return x("ARRAY_MAX(" + expression.toString() + ")");
    }

    public static Expression arrayMax(String expression) {
        return arrayMax(x(expression));
    }

    public static Expression arrayMax(JsonArray array) {
        return arrayMax(x(array));
    }

    public static Expression arrayMin(Expression expression) {
        return x("ARRAY_MIN(" + expression.toString() + ")");
    }

    public static Expression arrayMin(String expression) {
        return arrayMin(x(expression));
    }

    public static Expression arrayMin(JsonArray array) {
        return arrayMin(x(array));
    }

    public static Expression arrayPosition(Expression expression, Expression value) {
        return x("ARRAY_POSITION(" + expression.toString() + ", " + value.toString() + ")");
    }

    public static Expression arrayPosition(String expression, Expression value) {
        return arrayPosition(x(expression), value);
    }

    public static Expression arrayPosition(JsonArray array, Expression value) {
        return arrayPosition(x(array), value);
    }

    public static Expression arrayPrepend(Expression expression, Expression value) {
        return x("ARRAY_PREPEND(" + value.toString() + ", " + expression.toString() + ")");
    }

    public static Expression arrayPrepend(String expression, Expression value) {
        return arrayPrepend(x(expression), value);
    }

    public static Expression arrayPrepend(JsonArray array, Expression value) {
        return arrayPrepend(x(array), value);
    }

    public static Expression arrayPut(Expression expression, Expression value) {
        return x("ARRAY_PUT(" + expression.toString() + ", " + value.toString() + ")");
    }

    public static Expression arrayPut(String expression, Expression value) {
        return arrayPut(x(expression), value);
    }

    public static Expression arrayPut(JsonArray array, Expression value) {
        return arrayPut(x(array), value);
    }

    public static Expression arrayRange(long start, long end, long step) {
        return x("ARRAY_RANGE(" + start + ", " + end + ", " + step + ")");
    }

    public static Expression arrayRange(long start, long end) {
        return x("ARRAY_RANGE(" + start + ", " + end + ")");
    }

    public static Expression arrayRemove(Expression expression, Expression value) {
        return x("ARRAY_REMOVE(" + expression.toString() + ", " + value.toString() + ")");
    }

    public static Expression arrayRemove(String expression, Expression value) {
        return arrayRemove(x(expression), value);
    }

    public static Expression arrayRemove(JsonArray array, Expression value) {
        return arrayRemove(x(array), value);
    }

    public static Expression arrayRepeat(Expression value, long n) {
        return x("ARRAY_REPEAT(" + value.toString() + ", " + n + ")");
    }

    public static Expression arrayRepeat(String value, long n) {
        return arrayRepeat(s(value), n);
    }

    public static Expression arrayRepeat(Number value, long n) {
        return arrayRepeat(x(value), n);
    }

    public static Expression arrayRepeat(boolean value, long n) {
        return arrayRepeat(x(value), n);
    }


    public static Expression arrayReplace(Expression expression, Expression value1, Expression value2) {
        return x("ARRAY_REPLACE(" + expression.toString() + ", " + value1 + ", " + value2 + ")");
    }


    public static Expression arrayReplace(String expression, Expression value1, Expression value2) {
        return arrayReplace(x(expression), value1, value2);
    }


    public static Expression arrayReplace(JsonArray array, Expression value1, Expression value2) {
        return arrayReplace(x(array), value1, value2);
    }

    public static Expression arrayReplace(Expression expression, Expression value1, Expression value2, long n) {
        return x("ARRAY_REPLACE(" + expression.toString() + ", " + value1 + ", " + value2 + ", " + n + ")");
    }

    public static Expression arrayReplace(String expression, Expression value1, Expression value2, long n) {
        return arrayReplace(x(expression), value1, value2, n);
    }

    public static Expression arrayReplace(JsonArray array, Expression value1, Expression value2, long n) {
        return arrayReplace(x(array), value1, value2, n);
    }

    public static Expression arrayReverse(Expression expression) {
        return x("ARRAY_REVERSE(" + expression.toString() + ")");
    }

    public static Expression arrayReverse(String expression) {
        return arrayReverse(x(expression));
    }

    public static Expression arrayReverse(JsonArray array) {
        return arrayReverse(x(array));
    }

    public static Expression arraySort(Expression expression) {
        return x("ARRAY_SORT(" + expression.toString() + ")");
    }

    public static Expression arraySort(String expression) {
        return arraySort(x(expression));
    }

    public static Expression arraySort(JsonArray array) {
        return arraySort(x(array));
    }

    public static Expression arraySum(Expression expression) {
        return x("ARRAY_SUM(" + expression.toString() + ")");
    }

    public static Expression arraySum(String expression) {
        return arraySum(x(expression));
    }

    public static Expression arraySum(JsonArray array) {
        return arraySum(x(array));
    }
