	private void runEventLoop(Shell shell) {
		Display display = shell.getDisplay();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

