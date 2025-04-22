    public static final SortedMap EMPTY_SORTED_MAP = Collections
            .unmodifiableSortedMap(new TreeMap());

    public static final SortedSet EMPTY_SORTED_SET = Collections
            .unmodifiableSortedSet(new TreeSet());


    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Ensures that a string is not null. Converts null strings into empty
     * strings, and leaves any other string unmodified. Use this to help
     * wrap calls to methods that return null instead of the empty string.
     * Can also help protect against implementation errors in methods that
     * are not supposed to return null.
     *
     * @param input input string (may be null)
     * @return input if not null, or the empty string if input is null
     */
    public static String safeString(String input) {
        if (input != null) {
            return input;
        }

        return ZERO_LENGTH_STRING;
    }

    public static void assertInstance(Object object, Class c) {
        assertInstance(object, c, false);
    }

    public static void assertInstance(Object object, Class c, boolean allowNull) {
        if (object == null && allowNull) {
