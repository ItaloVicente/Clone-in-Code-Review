    /**
     * Gets an instance of <code>KeySequence</code> by parsing a given a
     * formal string representation.
     *
     * @param string
     *            the formal string representation to parse.
     * @return a key sequence. Guaranteed not to be <code>null</code>.
     * @throws ParseException
     *             if the given formal string representation could not be
     *             parsed to a valid key sequence.
     */
    public static KeySequence getInstance(String string) throws ParseException {
        if (string == null) {
