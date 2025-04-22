    public static Expression i(String... identifiers) {
        StringBuilder escaped = new StringBuilder();
        for (String i : identifiers) {
            escaped.append(", ");
            escaped.append('`').append(i).append('`');
        }
        if (escaped.length() > 2) {
            escaped.delete(0, 2);
        }
        return new Expression(escaped.toString());
    }

