		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = new Shell(display);
		shell.setSize(300, 300);
		shell.setLayout(new FillLayout());
		Composite inner = new Composite(shell, SWT.V_SCROLL);
		TableWrapLayout tableWrapLayout = new TableWrapLayout();
		tableWrapLayout.leftMargin = 0;
		tableWrapLayout.rightMargin = 0;
		inner.setLayout(tableWrapLayout);
