		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = new Shell(display);
		shell.setSize(100, 300);
		shell.setLayout(new FillLayout());
		Composite inner = new Composite(shell, SWT.V_SCROLL);
		inner.setLayout(new TableWrapLayout());
