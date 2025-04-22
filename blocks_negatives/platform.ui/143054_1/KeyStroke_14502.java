        return new KeyStroke(
                new TreeSet(Collections.singletonList(modifierKey)), naturalKey);
    }

    /**
     * Gets an instance of <code>KeyStroke</code> given an array of modifier
     * keys and a natural key.
     *
     * @param modifierKeys
     *            the array of modifier keys. This array may be empty, but it
     *            must not be <code>null</code>. If this array is not empty,
     *            it must not contain <code>null</code> elements.
     * @param naturalKey
     *            the natural key. May be <code>null</code>.
     * @return a key stroke. Guaranteed not to be <code>null</code>.
     */
    public static KeyStroke getInstance(ModifierKey[] modifierKeys,
            NaturalKey naturalKey) {
        Util.assertInstance(modifierKeys, ModifierKey.class);
        return new KeyStroke(new TreeSet(Arrays.asList(modifierKeys)),
                naturalKey);
    }

    /**
     * Gets an instance of <code>KeyStroke</code> given a natural key.
     *
     * @param naturalKey
     *            the natural key. May be <code>null</code>.
     * @return a key stroke. This key stroke will have no modifier keys.
     *         Guaranteed not to be <code>null</code>.
     */
    public static KeyStroke getInstance(NaturalKey naturalKey) {
        return new KeyStroke(Util.EMPTY_SORTED_SET, naturalKey);
    }

    /**
     * Gets an instance of <code>KeyStroke</code> given a set of modifier
     * keys and a natural key.
     *
     * @param modifierKeys
     *            the set of modifier keys. This set may be empty, but it must
     *            not be <code>null</code>. If this set is not empty, it
     *            must only contain instances of <code>ModifierKey</code>.
     * @param naturalKey
     *            the natural key. May be <code>null</code>.
     * @return a key stroke. Guaranteed not to be <code>null</code>.
     */
    public static KeyStroke getInstance(SortedSet modifierKeys,
            NaturalKey naturalKey) {
        return new KeyStroke(modifierKeys, naturalKey);
    }

    /**
     * Gets an instance of <code>KeyStroke</code> by parsing a given a formal
     * string representation.
     *
     * @param string
     *            the formal string representation to parse.
     * @return a key stroke. Guaranteed not to be <code>null</code>.
     * @throws ParseException
     *             if the given formal string representation could not be
     *             parsed to a valid key stroke.
     */
    public static KeyStroke getInstance(String string) throws ParseException {
        if (string == null) {
