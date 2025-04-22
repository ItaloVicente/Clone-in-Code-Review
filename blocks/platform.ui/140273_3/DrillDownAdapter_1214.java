	public void goBack() {
		Object currentInput = fChildTree.getInput();
		DrillFrame oFrame = fDrillStack.goBack();
		Object input = oFrame.getElement();
		fChildTree.setInput(input);
		expand(oFrame.getExpansion());
		if (fChildTree.getSelection().isEmpty()) {
			fChildTree.setSelection(new StructuredSelection(currentInput), true);
