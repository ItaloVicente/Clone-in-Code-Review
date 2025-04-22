
    public Expression add(Expression expression) {
        return infix("+", toString(), expression.toString());
    }

    public Expression add(Number b) {
        return add(x(String.valueOf(b)));
    }

    public Expression add(String expression) {
        return add(x(expression));
    }

    public Expression subtract(Expression expression) {
        return infix("-", toString(), expression.toString());
    }

    public Expression subtract(Number b) {
        return subtract(x(String.valueOf(b)));
    }

    public Expression subtract(String expression) {
        return subtract(x(expression));
    }

    public Expression multiply(Expression expression) {
        return infix("*", toString(), expression.toString());
    }

    public Expression multiply(Number b) {
        return multiply(x(String.valueOf(b)));
    }

    public Expression multiply(String expression) {
        return multiply(x(expression));
    }

    public Expression divide(Expression expression) {
        return infix("/", toString(), expression.toString());
    }

    public Expression divide(Number b) {
        return divide(x(String.valueOf(b)));
    }

    public Expression divide(String expression) {
        return divide(x(expression));
    }


