    public Expression gt(Expression right) {
        return infix(">", toString(), right.toString());
    }

    public Expression gt(String right) {
        return gt(x(right));
    }

    public Expression gt(int right) {
        return gt(x(right));
    }

    public Expression gt(long right) {
        return gt(x(right));
    }

    public Expression gt(float right) {
        return gt(x(right));
    }

    public Expression gt(double right) {
        return gt(x(right));
    }

    public Expression gt(boolean right) {
        return gt(x(right));
    }

    public Expression gt(JsonArray right) {
        return gt(x(right));
    }

    public Expression gt(JsonObject right) {
        return gt(x(right));
    }

    public Expression lt(Expression right) {
        return infix("<", toString(), right.toString());
    }

    public Expression lt(String right) {
        return lt(x(right));
    }

    public Expression lt(int right) {
        return lt(x(right));
    }

    public Expression lt(long right) {
        return lt(x(right));
    }

    public Expression lt(double right) {
        return lt(x(right));
    }

    public Expression lt(float right) {
        return lt(x(right));
    }

    public Expression lt(boolean right) {
        return lt(x(right));
    }

    public Expression lt(JsonObject right) {
        return lt(x(right));
    }

    public Expression lt(JsonArray right) {
        return lt(x(right));
    }

    public Expression gte(Expression right) {
        return infix(">=", toString(), right.toString());
    }

    public Expression gte(String right) {
        return gte(x(right));
    }

    public Expression gte(int right) {
        return gte(x(right));
    }

    public Expression gte(long right) {
        return gte(x(right));
    }

    public Expression gte(double right) {
        return gte(x(right));
    }

    public Expression gte(float right) {
        return gte(x(right));
    }

    public Expression gte(boolean right) {
        return gte(x(right));
    }

    public Expression gte(JsonObject right) {
        return gte(x(right));
    }

    public Expression gte(JsonArray right) {
        return gte(x(right));
    }

    public Expression concat(Expression right) {
        return infix("||", toString(), right.toString());
    }

    public Expression concat(String right) {
        return concat(x(right));
    }

    public Expression concat(int right) {
        return concat(x(right));
    }

    public Expression concat(long right) {
        return concat(x(right));
    }

    public Expression concat(boolean right) {
        return concat(x(right));
    }

    public Expression concat(float right) {
        return concat(x(right));
    }

    public Expression concat(double right) {
        return concat(x(right));
    }

    public Expression concat(JsonObject right) {
        return concat(x(right));
    }

    public Expression concat(JsonArray right) {
        return concat(x(right));
    }

    public Expression lte(Expression right) {
        return infix("<=", toString(), right.toString());
    }

    public Expression lte(String right) {
        return lte(x(right));
    }

    public Expression lte(int right) {
        return lte(x(right));
    }

    public Expression lte(long right) {
        return lte(x(right));
    }

    public Expression lte(float right) {
        return lte(x(right));
    }

    public Expression lte(double right) {
        return lte(x(right));
    }

    public Expression lte(boolean right) {
        return lte(x(right));
    }

    public Expression lte(JsonObject right) {
        return lte(x(right));
    }

    public Expression lte(JsonArray right) {
        return lte(x(right));
    }

    public Expression isValued() {
        return postfix("IS VALUED", toString());
    }

    public Expression isNotValued() {
        return postfix("IS NOT VALUED", toString());
    }

    public Expression isNull() {
        return postfix("IS NULL", toString());
    }

    public Expression isNotNull() {
        return postfix("IS NOT NULL", toString());
    }

    public Expression isMissing() {
        return postfix("IS MISSING", toString());
    }

    public Expression isNotMissing() {
        return postfix("IS NOT MISSING", toString());
    }

    public Expression between(Expression right) {
        return infix("BETWEEN", toString(), right.toString());
    }

    public Expression between(String right) {
        return between(x(right));
    }

    public Expression between(int right) {
        return between(x(right));
    }

    public Expression between(long right) {
        return between(x(right));
    }

    public Expression between(double right) {
        return between(x(right));
    }

    public Expression between(float right) {
        return between(x(right));
    }

    public Expression between(boolean right) {
        return between(x(right));
    }

    public Expression between(JsonObject right) {
        return between(x(right));
    }

    public Expression between(JsonArray right) {
        return between(x(right));
    }

    public Expression notBetween(Expression right) {
        return infix("NOT BETWEEN", toString(), right.toString());
    }

    public Expression notBetween(String right) {
        return notBetween(x(right));
    }

    public Expression notBetween(int right) {
        return notBetween(x(right));
    }

    public Expression notBetween(long right) {
        return notBetween(x(right));
    }

    public Expression notBetween(double right) {
        return notBetween(x(right));
    }

    public Expression notBetween(float right) {
        return notBetween(x(right));
    }

    public Expression notBetween(boolean right) {
        return notBetween(x(right));
    }

    public Expression notBetween(JsonObject right) {
        return notBetween(x(right));
    }

    public Expression notBetween(JsonArray right) {
        return notBetween(x(right));
    }

    public Expression like(Expression right) {
        return infix("LIKE", toString(), right.toString());
    }

    public Expression like(String right) {
        return like(x(right));
    }

    public Expression like(int right) {
        return like(x(right));
    }

    public Expression like(long right) {
        return like(x(right));
    }

    public Expression like(boolean right) {
        return like(x(right));
    }

    public Expression like(double right) {
        return like(x(right));
    }

    public Expression like(float right) {
        return like(x(right));
    }

    public Expression like(JsonObject right) {
        return like(x(right));
    }

    public Expression like(JsonArray right) {
        return like(x(right));
    }

    public Expression notLike(Expression right) {
        return infix("NOT LIKE", toString(), right.toString());
    }

    public Expression notLike(String right) {
        return notLike(x(right));
    }

    public Expression notLike(int right) {
        return notLike(x(right));
    }

    public Expression notLike(long right) {
        return notLike(x(right));
    }

    public Expression notLike(boolean right) {
        return notLike(x(right));
    }

    public Expression notLike(float right) {
        return notLike(x(right));
    }

    public Expression notLike(double right) {
        return notLike(x(right));
    }

    public Expression notLike(JsonObject right) {
        return notLike(x(right));
    }

    public Expression notLike(JsonArray right) {
        return notLike(x(right));
    }

    public Expression exists() {
        return prefix("EXISTS", toString());
    }

    public Expression in(Expression right) {
        return infix("IN", toString(), right.toString());
    }

    public Expression in(String right) {
        return in(x(right));
    }

    public Expression in(int right) {
        return in(x(right));
    }

    public Expression in(long right) {
        return in(x(right));
    }

    public Expression in(boolean right) {
        return in(x(right));
    }

    public Expression in(double right) {
        return in(x(right));
    }

    public Expression in(float right) {
        return in(x(right));
    }

    public Expression in(JsonObject right) {
        return in(x(right));
    }

    public Expression in(JsonArray right) {
        return in(x(right));
    }

    public Expression notIn(Expression right) {
        return infix("NOT IN", toString(), right.toString());
    }

    public Expression notIn(String right) {
        return notIn(x(right));
    }

    public Expression notIn(int right) {
        return notIn(x(right));
    }

    public Expression notIn(long right) {
        return notIn(x(right));
    }

    public Expression notIn(boolean right) {
        return notIn(x(right));
    }

    public Expression notIn(float right) {
        return notIn(x(right));
    }

    public Expression notIn(double right) {
        return notIn(x(right));
    }

    public Expression notIn(JsonObject right) {
        return notIn(x(right));
    }

    public Expression notIn(JsonArray right) {
        return notIn(x(right));
    }

    public Expression as(String alias) {
        return as(x(alias));
    }

    public Expression as(Expression alias) {
        return infix("AS", toString(), alias.toString());
    }

    private static Expression prefix(String prefix, String right) {
        return new Expression(prefix + " " + right);
    }

    private static Expression infix(String infix, String left, String right) {
        return new Expression(left + " " + infix + " " + right);
    }

    private static Expression postfix(String postfix, String left) {
        return new Expression(left + " " + postfix);
    }

    private static String wrapWith(char wrapper, String... input) {
        StringBuilder escaped = new StringBuilder();
        for (String i : input) {
            escaped.append(", ");
            escaped.append(wrapper).append(i).append(wrapper);
        }
        if (escaped.length() > 2) {
            escaped.delete(0, 2);
        }
        return escaped.toString();
