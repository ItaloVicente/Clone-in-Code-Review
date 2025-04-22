    public Expression get(String expression) {
        return new Expression(path(toString(), x(expression)));
    }

    public Expression get(Expression expression) {
        return get(expression.toString());
    }

