        return strToMillis(x(expression));
    }

    public static Expression millisToStr(Expression expression, String format) {
        if (format == null || format.isEmpty()) {
            return x("MILLIS_TO_STR(" + expression.toString() + ")");
        }
        return x("MILLIS_TO_STR(" + expression.toString() + ", \"" + format + "\")");
    }

    public static Expression millisToStr(String expression, String format) {
        return millisToStr(x(expression), format);
    }

    public static Expression millisToUtc(Expression expression, String format) {
        if (format == null || format.isEmpty()) {
            return x("MILLIS_TO_UTC(" + expression.toString() + ")");
        }
        return x("MILLIS_TO_UTC(" + expression.toString() + ", \"" + format + "\")");
