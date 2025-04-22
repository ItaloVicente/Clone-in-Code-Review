        List keyStrokes = new ArrayList(keySequence.getKeyStrokes());
        keyStrokes.add(keyStroke);
        return new KeySequence(keyStrokes);
    }

    /**
     * Gets an instance of <code>KeySequence</code> given a single key
     * stroke.
     *
     * @param keyStroke
     *            a single key stroke. Must not be <code>null</code>.
     * @return a key sequence. Guaranteed not to be <code>null</code>.
     */
    public static KeySequence getInstance(KeyStroke keyStroke) {
        return new KeySequence(Collections.singletonList(keyStroke));
    }

    /**
     * Gets an instance of <code>KeySequence</code> given an array of key
     * strokes.
     *
     * @param keyStrokes
     *            the array of key strokes. This array may be empty, but it
     *            must not be <code>null</code>. This array must not contain
     *            <code>null</code> elements.
     * @return a key sequence. Guaranteed not to be <code>null</code>.
     */
    public static KeySequence getInstance(KeyStroke[] keyStrokes) {
        return new KeySequence(Arrays.asList(keyStrokes));
    }

    /**
     * Gets an instance of <code>KeySequence</code> given a list of key
     * strokes.
     *
     * @param keyStrokes
     *            the list of key strokes. This list may be empty, but it must
     *            not be <code>null</code>. If this list is not empty, it
     *            must only contain instances of <code>KeyStroke</code>.
     * @return a key sequence. Guaranteed not to be <code>null</code>.
     */
    public static KeySequence getInstance(List keyStrokes) {
        return new KeySequence(keyStrokes);
    }
