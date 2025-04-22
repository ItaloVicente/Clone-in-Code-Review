	public void open() {
		model = createDefaultModel();

		final Display display = Display.getDefault();
		createContents();
		bindUI();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

