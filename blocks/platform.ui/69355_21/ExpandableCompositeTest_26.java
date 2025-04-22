	private void runEventLoop() {
		Display display = Display.getCurrent();

		while (shell != null && !shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (!display.isDisposed())
			display.update();
	}

