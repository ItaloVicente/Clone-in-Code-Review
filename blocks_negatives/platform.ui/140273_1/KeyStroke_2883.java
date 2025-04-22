    /**
     * The delimiter between multiple keys in a single key strokes -- expressed
     * in the formal key stroke grammar. This is not to be displayed to the
     * user. It is only intended as an internal representation.
     */

    /**
     * An internal constant used only in this object's hash code algorithm.
     */
    private static final int HASH_FACTOR = 89;

    /**
     * An internal constant used only in this object's hash code algorithm.
     */
    private static final int HASH_INITIAL = KeyStroke.class.getName()
            .hashCode();

    /**
     * The set of delimiters for <code>Key</code> objects allowed during
     * parsing of the formal string representation.
     */
    public static final String KEY_DELIMITERS = KEY_DELIMITER;

    /**
     * Gets an instance of <code>KeyStroke</code> given a single modifier key
     * and a natural key.
     *
     * @param modifierKey
     *            a modifier key. Must not be <code>null</code>.
     * @param naturalKey
     *            the natural key. May be <code>null</code>.
     * @return a key stroke. Guaranteed not to be <code>null</code>.
     */
    public static KeyStroke getInstance(ModifierKey modifierKey,
            NaturalKey naturalKey) {
        if (modifierKey == null) {
