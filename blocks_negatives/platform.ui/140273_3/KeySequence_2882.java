        try {
            return new KeySequence(keyStrokes);
        } catch (Throwable t) {
            throw new ParseException(
                            + keyStrokes);
        }
    }

    /**
     * The cached hash code for this object. Because <code>KeySequence</code>
     * objects are immutable, their hash codes need only to be computed once.
     * After the first call to <code>hashCode()</code>, the computed value
     * is cached here for all subsequent calls.
     */
    private transient int hashCode;

    /**
     * A flag to determine if the <code>hashCode</code> field has already
     * been computed.
     */
    private transient boolean hashCodeComputed;

    /**
     * The list of key strokes for this key sequence.
     */
    private List keyStrokes;

    /**
     * Constructs an instance of <code>KeySequence</code> given a list of key
     * strokes.
     *
     * @param keyStrokes
     *            the list of key strokes. This list may be empty, but it must
     *            not be <code>null</code>. If this list is not empty, it
     *            must only contain instances of <code>KeyStroke</code>.
     */
    private KeySequence(List keyStrokes) {
        this.keyStrokes = Util.safeCopy(keyStrokes, KeyStroke.class);

        for (int i = 0; i < this.keyStrokes.size() - 1; i++) {
            KeyStroke keyStroke = (KeyStroke) this.keyStrokes.get(i);

            if (!keyStroke.isComplete()) {
