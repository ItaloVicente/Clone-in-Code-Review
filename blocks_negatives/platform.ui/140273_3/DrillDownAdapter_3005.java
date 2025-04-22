    /**
     * Reverts the input for the tree back to the state when <code>goInto</code>
     * was last called.
     * <p>
     * A frame is removed from the drill stack.  Then that frame is used to reset the
     * input and expansion state for the child tree.
     * </p>
     */
    public void goBack() {
        Object currentInput = fChildTree.getInput();
        DrillFrame oFrame = fDrillStack.goBack();
        Object input = oFrame.getElement();
        fChildTree.setInput(input);
        expand(oFrame.getExpansion());
        if (fChildTree.getSelection().isEmpty()) {
			fChildTree
                    .setSelection(new StructuredSelection(currentInput), true);
