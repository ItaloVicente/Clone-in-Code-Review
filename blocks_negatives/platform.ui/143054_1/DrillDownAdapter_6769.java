    /**
     * Sets the input for the tree to a particular item in the tree.
     * <p>
     * The current input and expansion state are saved in a frame and added to the
     * drill stack.  Then the input for the tree is changed to be <code>newInput</code>.
     * The expansion state for the tree is maintained during the operation.
     * </p><p>
     * On return the client may revert back to the previous state by invoking
     * <code>goBack</code> or <code>goHome</code>.
     * </p>
     *
     * @param newInput the new input element
     */
    public void goInto(Object newInput) {
        if (canExpand(newInput)) {
            Object oldInput = fChildTree.getInput();
            List expandedList = getExpanded();
            fDrillStack.add(new DrillFrame(oldInput, "null", expandedList));//$NON-NLS-1$
