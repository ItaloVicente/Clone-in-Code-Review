        return keyStrokes.equals(((KeySequence) object).keyStrokes);
    }

    /**
     * Formats this key sequence into the current default look.
     *
     * @return A string representation for this key sequence using the default
     *         look; never <code>null</code>.
     */
    public String format() {
        return KeyFormatterFactory.getDefault().format(this);
    }

    /**
     * Returns the list of key strokes for this key sequence.
     *
     * @return the list of key strokes keys. This list may be empty, but is
     *         guaranteed not to be <code>null</code>. If this list is not
     *         empty, it is guaranteed to only contain instances of <code>KeyStroke</code>.
     */
    public List getKeyStrokes() {
        return keyStrokes;
    }

    @Override
