    public static Expression clockMillis() {
        return x("CLOCK_MILLIS()");
    }

    public static Expression clockStr(String format) {
        if (format == null || format.isEmpty()) {
            return x("CLOCK_STR()");
        }
        return x("CLOCK_STR(\"" + format + "\")");
    }

    public static Expression dateAddMillis(Expression expression, int n, DatePart part) {
        return x("DATE_ADD_MILLIS(" + expression.toString() + ", " + n + ", \"" + part + "\")");
    }

    public static Expression dateAddMillis(String expression, int n, DatePart part) {
        return dateAddMillis(x(expression), n, part);
    }

    public static Expression dateAddStr(Expression expression, int n, DatePart part) {
        return x("DATE_ADD_STR(" + expression.toString() + ", " + n + ", \"" + part + "\")");
    }

    public static Expression dateAddStr(String expression, int n, DatePart part) {
        return dateAddStr(x(expression), n, part);
    }

    public static Expression dateDiffMillis(Expression expression1, Expression expression2, DatePart part) {
        return x("DATE_DIFF_MILLIS(" + expression1.toString() + ", " + expression2.toString()
                + ", \"" + part.toString() + "\")");
    }

    public static Expression dateDiffMillis(String expression1, String expression2, DatePart part) {
        return dateDiffMillis(x(expression1), x(expression2), part);
    }

    public static Expression dateDiffStr(Expression expression1, Expression expression2, DatePart part) {
        return x("DATE_DIFF_STR(" + expression1.toString() + ", " + expression2.toString()
                + ", \"" + part.toString() + "\")");
    }

    public static Expression dateDiffStr(String expression1, String expression2, DatePart part) {
        return dateDiffStr(x(expression1), x(expression2), part);
    }

    public static Expression datePartMillis(Expression expression, DatePartExt part) {
        return x("DATE_PART_MILLIS(" + expression.toString() + ", \"" + part.toString() + "\")");
    }

    public static Expression datePartMillis(String expression, DatePartExt part) {
        return datePartMillis(x(expression), part);
    }

    public static Expression datePartStr(Expression expression, DatePartExt part) {
        return x("DATE_PART_STR(" + expression.toString() + ", \"" + part.toString() + "\")");
    }

    public static Expression datePartStr(String expression, DatePartExt part) {
        return datePartStr(x(expression), part);
    }

    public static Expression dateTruncMillis(Expression expression, DatePart part) {
        return x("DATE_TRUNC_MILLIS(" + expression.toString() + ", \"" + part.toString() + "\")");
    }

    public static Expression dateTruncMillis(String expression, DatePart part) {
        return dateTruncMillis(x(expression), part);
    }

    public static Expression dateTruncStr(Expression expression, DatePart part) {
        return x("DATE_TRUNC_STR(" + expression.toString() + ", \"" + part.toString() + "\")");
    }

    public static Expression dateTruncStr(String expression, DatePart part) {
        return dateTruncStr(x(expression), part);
    }

    public static Expression millis(Expression expression) {
        return x("MILLIS(" + expression.toString() + ")");
    }

    public static Expression millis(String expression) {
        return millis(x(expression));
    }
