    public static Expression abs(Expression expression) {
        return x("ABS(" + expression.toString() + ")");
    }

    public static Expression abs(Number value) {
        return abs(x(value));
    }

    public static Expression acos(Expression expression) {
        return x("ACOS(" + expression.toString() + ")");
    }

    public static Expression acos(Number value) {
        return acos(x(value));
    }

    public static Expression asin(Expression expression) {
        return x("ASIN(" + expression.toString() + ")");
    }

    public static Expression asin(Number value) {
        return asin(x(value));
    }

    public static Expression atan(Expression expression) {
        return x("ATAN(" + expression.toString() + ")");
    }

    public static Expression atan(Number value) {
        return atan(x(value));
    }

    public static Expression atan(Expression expression1, Expression expression2) {
        return x("ATAN(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression atan(String expression1, String expression2) {
        return atan(x(expression1), x(expression2));
    }

    public static Expression ceil(Expression expression) {
        return x("CEIL(" + expression.toString() + ")");
    }

    public static Expression ceil(Number value) {
        return ceil(x(value));
    }

    public static Expression cos(Expression expression) {
        return x("COS(" + expression.toString() + ")");
    }

    public static Expression cos(Number value) {
        return cos(x(value));
    }

    public static Expression degrees(Expression expression) {
        return x("DEGREES(" + expression.toString() + ")");
    }

    public static Expression degrees(Number value) {
        return degrees(x(value));
    }

    public static Expression e() {
        return x("E()");
    }

    public static Expression exp(Expression expression) {
        return x("EXP(" + expression.toString() + ")");
    }

    public static Expression exp(Number value) {
        return exp(x(value));
    }

    public static Expression ln(Expression expression) {
        return x("LN(" + expression.toString() + ")");
    }

    public static Expression ln(Number value) {
        return ln(x(value));
    }

    public static Expression log(Expression expression) {
        return x("LOG(" + expression.toString() + ")");
    }

    public static Expression log(Number value) {
        return log(x(value));
    }

    public static Expression floor(Expression expression) {
        return x("FLOOR(" + expression.toString() + ")");
    }

    public static Expression floor(Number value) {
        return floor(x(value));
    }

    public static Expression pi() {
        return x("PI()");
    }

    public static Expression power(Expression expression1, Expression expression2) {
        return x("POWER(" + expression1.toString() + ", " + expression2.toString() + ")");
    }

    public static Expression power(Number value1, Number value2) {
        return power(x(value1), x(value2));
    }

    public static Expression radians(Expression expression) {
        return x("RADIANS(" + expression.toString() + ")");
    }

    public static Expression radians(Number value) {
        return radians(x(value));
    }

    public static Expression random(Expression seed) {
        return x("RANDOM(" + seed.toString() + ")");
    }

    public static Expression random(Number seed) {
        return random(x(seed));
    }

    public static Expression random() {
        return x("RANDOM()");
    }

