    /**
     * Sets the text for this action.
     * <p>
     * An accelerator is identified by the last index of a '\t' character. If
     * there are no '\t' characters, then it is identified by the last index of an
     * '@' character. If neither, then there is no accelerator text. Note that
     * if you want to insert an '@' character into the text (but no accelerator),
     * then you can simply insert an '@' or a '\t' at the end of the text.
     * <br>
     * An accelerator specification consists of zero or more
     * modifier tokens followed by a key code token.  The tokens are separated by a '+' character.
     * </p>
     * <p>
     * Fires a property change event for the <code>TEXT</code> property
     * if the text actually changes as a consequence.
     * </p>
     *
     * @param text the text, or <code>null</code> if none
     * @see #TEXT
     * @see Action#findModifier
     * @see Action#findKeyCode
     */
