	public static final String KEY_DELIMITER = "\u002B"; //$NON-NLS-1$

	private static final int HASH_FACTOR = 89;

	private static final int HASH_INITIAL = KeyStroke.class.getName().hashCode();

	public static final String KEY_DELIMITERS = KEY_DELIMITER;

	public static KeyStroke getInstance(ModifierKey modifierKey, NaturalKey naturalKey) {
		if (modifierKey == null) {
