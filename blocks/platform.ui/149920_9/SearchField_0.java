	private ToolItem createQuickAccessToolbar(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.marginLeft = layout.marginRight = 8;
		layout.marginBottom = 0;
		layout.marginTop = 0;
		comp.setLayout(layout);
		ToolBar toolbar = new ToolBar(comp, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem quickAccessToolItem = new ToolItem(toolbar, SWT.PUSH);

