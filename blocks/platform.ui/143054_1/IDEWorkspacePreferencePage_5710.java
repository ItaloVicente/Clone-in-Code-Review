	private void createSaveIntervalGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		groupComposite.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		groupComposite.setLayoutData(gd);
