    /**
     * Reverts the input for the tree back to the state when the adapter was
     * created.
     * <p>
     * All of the frames are removed from the drill stack.  Then the oldest frame is
     * used to reset the input and expansion state for the child tree.
     * </p>
     */
    public void goHome() {
        Object currentInput = fChildTree.getInput();
        DrillFrame oFrame = fDrillStack.goHome();
        Object input = oFrame.getElement();
        fChildTree.setInput(input);
        expand(oFrame.getExpansion());
        if (fChildTree.getSelection().isEmpty()) {
			fChildTree
                    .setSelection(new StructuredSelection(currentInput), true);
