    /**
     * <p>
     * Resets the state based on the current properties. If the state is to
     * collapse fully or if there are no key strokes, then it sets the state to
     * have an empty key sequence. Otherwise, it leaves the first key stroke in
     * the sequence.
     * </p>
     * <p>
     * The workbench's status lines are updated, if appropriate.
     * </p>
     */
    void reset() {
        currentSequence = KeySequence.getInstance();
        updateStatusLines();
    }
