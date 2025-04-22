    /**
     * Sets the input for the tree to the current selection.
     * <p>
     * The current input and expansion state are saved in a frame and added to the
     * drill stack.  Then the input for the tree is changed to be the current selection.
     * The expansion state for the tree is maintained during the operation.
     * </p><p>
     * On return the client may revert back to the previous state by invoking
     * <code>goBack</code> or <code>goHome</code>.
     * </p>
     */
    public void goInto() {
