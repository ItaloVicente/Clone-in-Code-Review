    public static String asString(RGB value) {
        Assert.isNotNull(value);
        StringBuilder buffer = new StringBuilder();
        buffer.append(value.red);
        buffer.append(',');
        buffer.append(value.green);
        buffer.append(',');
        buffer.append(value.blue);
        return buffer.toString();
    }

    /**
     * Converts the given boolean value to a string.
     * Equivalent to <code>String.valueOf(value)</code>.
     *
     * @param value the boolean value
     * @return the string representing the given boolean
     */
    public static String asString(boolean value) {
        return String.valueOf(value);
    }

    /**
     * Returns the given string with all whitespace characters removed.
     * <p>
     * All characters that have codes less than or equal to <code>'&#92;u0020'</code>
     * (the space character) are considered to be a white space.
     * </p>
     *
     * @param s the source string
     * @return the string with all whitespace characters removed
     */
    public static String removeWhiteSpaces(String s) {
        boolean found = false;
        int wsIndex = -1;
        int size = s.length();
        for (int i = 0; i < size; i++) {
            found = Character.isWhitespace(s.charAt(i));
            if (found) {
                wsIndex = i;
                break;
            }
        }
        if (!found) {
