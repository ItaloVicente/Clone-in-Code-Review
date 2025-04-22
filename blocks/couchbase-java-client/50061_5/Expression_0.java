    public static Expression x(final Statement statement) {
        return x(statement.toString());
    }

    public static Expression x(final Number number) {
        return x(String.valueOf(number));
    }

    public static Expression sub(final Statement statement) {
        return x("(" + statement.toString() + ")");
    }

    public static Expression path(Object... pathComponents) {
        if (pathComponents == null || pathComponents.length == 0) {
            return EMPTY_INSTANCE;
        }
        StringBuilder path = new StringBuilder();
        for (Object p : pathComponents) {
            path.append('.');
            if (p instanceof Expression) {
                path.append(((Expression) p).toString());
            } else {
                path.append(String.valueOf(p));
            }
        }
        path.deleteCharAt(0);
        return x(path.toString());
    }

