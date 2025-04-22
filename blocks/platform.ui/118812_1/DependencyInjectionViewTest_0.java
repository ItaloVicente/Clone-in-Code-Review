
	private void processViewEvents(List<String> expectedCalls, List<String> actualCalls) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return !display.readAndDispatch() && expectedCalls.size() == actualCalls.size();
			}
		}.waitForCondition(display, 3000);
	}
