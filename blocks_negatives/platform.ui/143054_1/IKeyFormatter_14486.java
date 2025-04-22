    /**
     * Format the given key sequence into a string. The manner of the
     * conversion is dependent on the formatter. It is required that unequal
     * key seqeunces return unequal strings.
     *
     * @param keySequence
     *            The key sequence to convert; must not be <code>null</code>.
     * @return A string representation of the key sequence; must not be <code>null</code>.
     */
    String format(KeySequence keySequence);
