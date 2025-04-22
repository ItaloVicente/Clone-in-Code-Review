    /**
     * A mutator for the partial sequence entered by the user.
     *
     * @param sequence
     *            The current key sequence; should not be <code>null</code>,
     *            but may be empty.
     */
    void setCurrentSequence(KeySequence sequence) {
        currentSequence = sequence;
        updateStatusLines();
    }
