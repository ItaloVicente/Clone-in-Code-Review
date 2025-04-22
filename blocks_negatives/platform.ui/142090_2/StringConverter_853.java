    private StringConverter() {
    }

    /**
     * Breaks out space-separated words into an array of words.
     * For example: <code>"no comment"</code> into an array
     * <code>a[0]="no"</code> and <code>a[1]= "comment"</code>.
     *
     * @param value the string to be converted
     * @return the list of words
     * @throws DataFormatException thrown if request string could not seperated
     */
    public static String[] asArray(String value) throws DataFormatException {
        ArrayList<String> list = new ArrayList<>();
        StringTokenizer stok = new StringTokenizer(value);
        while (stok.hasMoreTokens()) {
            list.add(stok.nextToken());
        }
        String result[] = new String[list.size()];
        list.toArray(result);
        return result;
    }

    /**
     /**
     * Breaks out space-separated words into an array of words.
     * For example: <code>"no comment"</code> into an array
     * <code>a[0]="no"</code> and <code>a[1]= "comment"</code>.
     * Returns the given default value if the value cannot be parsed.
     *
     * @param value the string to be converted
     * @param dflt the default value
     * @return the list of words, or the default value
     */
    public static String[] asArray(String value, String[] dflt) {
        try {
            return asArray(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into a boolean.
     * This method fails if the value does not represent a boolean.
     * <p>
     * Valid representations of <code>true</code> include the strings
     * "<code>t</code>", "<code>true</code>", or equivalent in mixed
     * or upper case.
     * Similarly, valid representations of <code>false</code> include the strings
     * "<code>f</code>", "<code>false</code>", or equivalent in mixed
     * or upper case.
     * </p>
     *
     * @param value the value to be converted
     * @return the value as a boolean
     * @exception DataFormatException if the given value does not represent
     *	a boolean
     */
    public static boolean asBoolean(String value) throws DataFormatException {
        String v = value.toLowerCase();
