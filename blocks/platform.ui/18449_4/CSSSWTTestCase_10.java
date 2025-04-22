	@Override
	protected void tearDown() throws Exception {
		Display display = Display.getDefault();
		if (!display.isDisposed()) {
			for (Shell shell : display.getShells()) {
				shell.dispose();
			}
		}
		super.tearDown();
	}
