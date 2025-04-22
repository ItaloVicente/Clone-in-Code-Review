    /**
     * The delimiter between multiple key strokes in a single key sequence --
     * expressed in the formal key stroke grammar. This is not to be displayed
     * to the user. It is only intended as an internal representation.
     */

    /**
     * An empty key sequence instance for use by everyone.
     */
    private static final KeySequence EMPTY_KEY_SEQUENCE = new KeySequence(
            Collections.EMPTY_LIST);

    /**
     * An internal constant used only in this object's hash code algorithm.
     */
    private static final int HASH_FACTOR = 89;

    /**
     * An internal constant used only in this object's hash code algorithm.
     */
    private static final int HASH_INITIAL = KeySequence.class.getName()
            .hashCode();

    /**
     * The set of delimiters for <code>KeyStroke</code> objects allowed
     * during parsing of the formal string representation.
     */
    public static final String KEY_STROKE_DELIMITERS = KEY_STROKE_DELIMITER

    /**
     * Gets an instance of <code>KeySequence</code>.
     *
     * @return a key sequence. This key sequence will have no key strokes.
     *         Guaranteed not to be <code>null</code>.
     */
    public static KeySequence getInstance() {
        return EMPTY_KEY_SEQUENCE;
    }

    /**
     * Gets an instance of <code>KeySequence</code> given a key sequence and
     * a key stroke.
     *
     * @param keySequence
     *            a key sequence. Must not be <code>null</code>.
     * @param keyStroke
     *            a key stroke. Must not be <code>null</code>.
     * @return a key sequence that is equal to the given key sequence with the
     *         given key stroke appended to the end. Guaranteed not to be
     *         <code>null</code>.
     */
    public static KeySequence getInstance(KeySequence keySequence,
            KeyStroke keyStroke) {
        if (keySequence == null || keyStroke == null) {
