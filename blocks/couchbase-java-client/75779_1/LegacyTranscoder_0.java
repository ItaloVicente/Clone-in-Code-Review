
    private static boolean isJsonObject(final String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        if (s.startsWith("{") || s.startsWith("[")
            || "true".equals(s) || "false".equals(s)
            || "null".equals(s) || DECIMAL_PATTERN.matcher(s).matches()) {
            return true;
        }

        return false;
    }
