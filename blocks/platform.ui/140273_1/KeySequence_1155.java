	public static final String KEY_STROKE_DELIMITER = "\u0020"; //$NON-NLS-1$

	private static final KeySequence EMPTY_KEY_SEQUENCE = new KeySequence(Collections.EMPTY_LIST);

	private static final int HASH_FACTOR = 89;

	private static final int HASH_INITIAL = KeySequence.class.getName().hashCode();

	public static final String KEY_STROKE_DELIMITERS = KEY_STROKE_DELIMITER + "\b\r\u007F\u001B\f\n\0\t\u000B"; //$NON-NLS-1$

	public static KeySequence getInstance() {
		return EMPTY_KEY_SEQUENCE;
	}

	public static KeySequence getInstance(KeySequence keySequence, KeyStroke keyStroke) {
		if (keySequence == null || keyStroke == null) {
