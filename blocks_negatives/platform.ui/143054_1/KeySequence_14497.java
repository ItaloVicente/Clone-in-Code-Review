        KeySequence castedObject = (KeySequence) object;
        int compareTo = Util.compare(keyStrokes, castedObject.keyStrokes);
        return compareTo;
    }

    /**
     * Returns whether or not this key sequence ends with the given key
     * sequence.
     *
     * @param keySequence
     *            a key sequence. Must not be <code>null</code>.
     * @param equals
     *            whether or not an identical key sequence should be considered
     *            as a possible match.
     * @return <code>true</code>, iff the given key sequence ends with this
     *         key sequence.
     */
    public boolean endsWith(KeySequence keySequence, boolean equals) {
        if (keySequence == null) {
