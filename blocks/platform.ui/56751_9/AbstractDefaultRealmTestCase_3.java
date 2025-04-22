	protected void runAsync() {
		Display display = Display.getCurrent();

		while (display.readAndDispatch()) {
		}
	}

