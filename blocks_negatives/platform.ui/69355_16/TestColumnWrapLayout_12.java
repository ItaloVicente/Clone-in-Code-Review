		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = new Shell(display);
		shell.setSize(100, 300);
		shell.setLayout(new GridLayout());
		Composite inner = new Composite(shell, SWT.NULL);
		ColumnLayout layout = new ColumnLayout();
