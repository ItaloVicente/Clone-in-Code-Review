	
	CSSEngine engine;
		
	protected Label createTestLabel(String styleSheet) {
		Display display = Display.getDefault();
		engine = createEngine(styleSheet, display);
		
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Composite panel = new Composite(shell, SWT.NONE);
		panel.setLayout(new FillLayout());

		Label labelToTest = new Label(panel, SWT.NONE);
		labelToTest.setText("Some label text");
