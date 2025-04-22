    public static Expression contains(Expression expression, String substring) {
        return x("CONTAINS(" + expression.toString() + ", \"" + substring + "\")");
    }

    public static Expression contains(String expression, String substring) {
        return contains(x(expression), substring);
    }

    public static Expression initCap(Expression expression) {
        return x("INITCAP(" + expression.toString() + ")");
    }

    public static Expression initCap(String expression) {
        return initCap(x(expression));
    }

    public static Expression title(Expression expression) {
        return x("TITLE(" + expression.toString() + ")");
    }

    public static Expression title(String expression) {
        return title(x(expression));
    }

    public static Expression length(Expression expression) {
        return x("LENGTH(" + expression.toString() + ")");
    }

    public static Expression length(String expression) {
        return length(x(expression));
    }

