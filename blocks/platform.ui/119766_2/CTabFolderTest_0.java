	@SuppressWarnings("restriction")
	protected Label createLabelInCTabFolder(String styleSheet) {
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		Composite panel = new Composite(shell, SWT.NONE);
		panel.setLayout(new FillLayout());

		CTabFolder folderToTest = new CTabFolder(panel, SWT.NONE);
		CTabItem tab1 = new CTabItem(folderToTest, SWT.NONE);
		tab1.setText("A TAB ITEM");
		Composite composite = new Composite(folderToTest, SWT.BORDER);
		composite.setLayout(new FillLayout());
		Label labelToTest = new Label(composite, SWT.BORDER);
		labelToTest.setText("Text for item ");
		tab1.setControl(composite);
		folderToTest.setSelection(0);

		engine.applyStyles(shell, true);

		shell.pack();
		return labelToTest;
	}

