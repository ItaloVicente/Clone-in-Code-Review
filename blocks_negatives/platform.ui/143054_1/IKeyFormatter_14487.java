    /**
     * Format the given key strokes into a string. The manner of the conversion
     * is dependent on the formatter. It is required that unequal key strokes
     * return unequal strings.
     *
     * @param keyStroke
     *            The key stroke to convert; must not be <Code>null</code>.
     * @return A string representation of the key stroke; must not be <code>
     *         null</code>
     */
    String format(KeyStroke keyStroke);
