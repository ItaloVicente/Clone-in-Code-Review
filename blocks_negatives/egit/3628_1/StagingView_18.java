	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		repositoryLabel = new Label(parent, SWT.NONE);
		repositoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
