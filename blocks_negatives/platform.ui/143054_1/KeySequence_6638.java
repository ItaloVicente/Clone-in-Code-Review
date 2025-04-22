        if (!hashCodeComputed) {
            hashCode = HASH_INITIAL;
            hashCode = hashCode * HASH_FACTOR + keyStrokes.hashCode();
            hashCodeComputed = true;
        }

        return hashCode;
    }

    /**
     * Returns whether or not this key sequence is complete. Key sequences are
     * complete iff all of their key strokes are complete.
     *
     * @return <code>true</code>, iff the key sequence is complete.
     */
    public boolean isComplete() {
        return keyStrokes.isEmpty()
                || ((KeyStroke) keyStrokes.get(keyStrokes.size() - 1))
                        .isComplete();
    }

    /**
     * Returns whether or not this key sequence is empty. Key sequences are
     * complete iff they have no key strokes.
     *
     * @return <code>true</code>, iff the key sequence is empty.
     */
    public boolean isEmpty() {
        return keyStrokes.isEmpty();
    }

    /**
     * Returns whether or not this key sequence starts with the given key
     * sequence.
     *
     * @param keySequence
     *            a key sequence. Must not be <code>null</code>.
     * @param equals
     *            whether or not an identical key sequence should be considered
     *            as a possible match.
     * @return <code>true</code>, iff the given key sequence starts with
     *         this key sequence.
     */
    public boolean startsWith(KeySequence keySequence, boolean equals) {
        if (keySequence == null) {
