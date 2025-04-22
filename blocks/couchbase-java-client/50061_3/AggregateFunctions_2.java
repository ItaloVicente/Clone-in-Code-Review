
    public static Expression sum(Expression expression) {
        return x("SUM(" + expression.toString() + ")");
    }

    public static Expression sum(String expression) {
        return sum(x(expression));
    }

    public static Expression distinct(Expression expression) {
        return x("DISTINCT " + expression.toString());
    }

    public static Expression distinct(String expression) {
        return x("DISTINCT " + expression);
    }


