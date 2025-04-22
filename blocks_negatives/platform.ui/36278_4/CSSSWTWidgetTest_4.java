	CSSEngine engine;
	
	protected Widget createTestLabel(String styleSheet) {
		Display display = Display.getDefault();
		engine = createEngine(styleSheet, display);	
		
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
