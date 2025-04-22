
	private void processUiEvents() {
		while (fWorkbench.getDisplay().readAndDispatch()) {
			;
		}
	}
