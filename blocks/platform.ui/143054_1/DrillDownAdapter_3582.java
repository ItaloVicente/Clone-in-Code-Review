	public void goHome() {
		Object currentInput = fChildTree.getInput();
		DrillFrame oFrame = fDrillStack.goHome();
		Object input = oFrame.getElement();
		fChildTree.setInput(input);
		expand(oFrame.getExpansion());
		if (fChildTree.getSelection().isEmpty()) {
			fChildTree.setSelection(new StructuredSelection(currentInput), true);
