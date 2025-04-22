
    public static Expression sign(Expression expression) {
        return x("SIGN(" + expression.toString() + ")");
    }

    public static Expression sign(Number value) {
        return sign(x(value));
    }

    public static Expression sin(Expression expression) {
        return x("SIN(" + expression.toString() + ")");
    }

    public static Expression sin(Number value) {
        return sin(x(value));
    }

    public static Expression squareRoot(Expression expression) {
        return x("SQRT(" + expression.toString() + ")");
    }

    public static Expression squareRoot(Number value) {
        return squareRoot(x(value));
    }

    public static Expression tan(Expression expression) {
        return x("TAN(" + expression.toString() + ")");
    }

    public static Expression tan(Number value) {
        return tan(x(value));
    }

    public static Expression trunc(Expression expression, int digits) {
        return x("TRUNC(" + expression.toString() + ", " + digits + ")");
    }

    public static Expression trunc(Number value, int digits) {
        return trunc(x(value), digits);
    }

    public static Expression trunc(Expression expression) {
        return x("TRUNC(" + expression.toString() + ")");
    }

    public static Expression trunc(Number value) {
        return trunc(x(value));
    }

