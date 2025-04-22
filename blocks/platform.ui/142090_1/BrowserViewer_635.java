	public BrowserViewer(Composite parent, int style) {
		super(parent, SWT.NONE);

		if ((style & LOCATION_BAR) != 0)
			showURLbar = true;

		if ((style & BUTTON_BAR) != 0)
			showToolbar = true;

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.numColumns = 1;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));
		clipboard = new Clipboard(parent.getDisplay());
