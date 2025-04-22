	protected void keepOpen(Display display, Shell dialog) {
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

