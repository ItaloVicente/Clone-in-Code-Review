	private Link createTestLink(String styleSheet) {
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Composite panel = new Composite(shell, SWT.NONE);
		panel.setLayout(new FillLayout());

		Link labelToTest = new Link(panel, SWT.NONE);
		labelToTest.setText("Some text <A HREF='./somewhere'>some link text</A>");

		engine.applyStyles(labelToTest, true);

		shell.pack();
		return labelToTest;
	}

