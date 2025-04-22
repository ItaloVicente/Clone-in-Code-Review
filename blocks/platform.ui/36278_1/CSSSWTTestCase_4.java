

	@After
	public void tearDown() {
		Display display = Display.getDefault();
		if (!display.isDisposed()) {
			for (Shell shell : display.getShells()) {
				shell.dispose();
			}
