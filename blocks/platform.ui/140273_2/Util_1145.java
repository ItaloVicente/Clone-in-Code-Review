	public static final SortedMap EMPTY_SORTED_MAP = Collections.unmodifiableSortedMap(new TreeMap());

	public static final SortedSet EMPTY_SORTED_SET = Collections.unmodifiableSortedSet(new TreeSet());

	public static final String ZERO_LENGTH_STRING = ""; //$NON-NLS-1$

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

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
