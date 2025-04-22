    public static Expression count(Expression expression) {
        return x("COUNT(" + expression.toString() + ")");
    }

    public static Expression count(String expression) {
        return count(x(expression));
    }

    public static Expression countAll() {
        return x("COUNT(*)");
    }

    public static Expression max(Expression expression) {
        return x("MAX(" + expression.toString() + ")");
    }

    public static Expression max(String expression) {
        return max(x(expression));
    }

    public static Expression min(Expression expression) {
        return x("MIN(" + expression.toString() + ")");
    }

    public static Expression min(String expression) {
        return min(x(expression));
    }

