    public static Expression ltrim(Expression expression) {
        return x("LTRIM(" + expression.toString() + ")");
    }

    public static Expression ltrim(String expression) {
        return ltrim(x(expression));
    }

    public static Expression ltrim(Expression expression, String characters) {
        return x("LTRIM(" + expression.toString() + ", \"" + characters + "\")");
    }

    public static Expression ltrim(String expression, String characters) {
        return ltrim(x(expression), characters);
    }

    public static Expression position(Expression expression, String substring) {
        return x("POSITION(" + expression.toString() + ", \"" + substring + "\")");
    }

    public static Expression position(String expression, String substring) {
        return position(x(expression), substring);
    }

    public static Expression repeat(Expression expression, int n) {
        return x("REPEAT(" + expression.toString() + ", " + n + ")");
    }

    public static Expression repeat(String expression, int n) {
        return repeat(x(expression), n);
    }

    public static Expression replace(Expression expression, String substring, String repl) {
        return x("REPLACE(" + expression.toString() + ", \"" + substring + "\", \"" + repl + "\")");
    }

    public static Expression replace(String expression, String substring, String repl) {
        return replace(x(expression), substring, repl);
    }

    public static Expression replace(Expression expression, String substring, String repl, int n) {
        return x("REPLACE(" + expression.toString() + ", \"" + substring + "\", \"" + repl + "\", " + n + ")");
    }

    public static Expression replace(String expression, String substring, String repl, int n) {
        return replace(x(expression), substring, repl, n);
    }

    public static Expression rtrim(Expression expression) {
        return x("RTRIM(" + expression.toString() + ")");
    }

    public static Expression rtrim(String expression) {
        return rtrim(x(expression));
    }

    public static Expression rtrim(Expression expression, String characters) {
        return x("RTRIM(" + expression.toString() + ", \"" + characters + "\")");
    }

    public static Expression rtrim(String expression, String characters) {
        return rtrim(x(expression), characters);
    }

    public static Expression split(Expression expression) {
        return x("SPLIT(" + expression.toString() + ")");
    }

    public static Expression split(String expression) {
        return split(x(expression));
    }

    public static Expression split(Expression expression, String sep) {
        return x("SPLIT(" + expression.toString() + ", \"" + sep + "\")");
    }

    public static Expression split(String expression, String sep) {
        return split(x(expression), sep);
    }

