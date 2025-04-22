    /**
     * An accessor for the current key sequence waiting for completion.
     *
     * @return The current incomplete key sequence; never <code>null</code>,
     *         but may be empty.
     */
    KeySequence getCurrentSequence() {
        return currentSequence;
    }
