        return text.getCharCount() > 0;
    }

    /**
     * Processes a key release event that occurred in this cell editor.
     * <p>
     * The <code>TextCellEditor</code> implementation of this framework method
     * ignores when the RETURN key is pressed since this is handled in
     * <code>handleDefaultSelection</code>.
     * An exception is made for Ctrl+Enter for multi-line texts, since
     * a default selection event is not sent in this case.
     * </p>
     *
     * @param keyEvent the key event
     */
    @Override
