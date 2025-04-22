	private void createTableGroup() {
		final Group tableGroup = new Group(panel, SWT.NONE);
		tableGroup.setText(NLS.bind(UIText.RefSpecPanel_specifications,
				typeString()));
		tableGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableGroup.setLayout(new GridLayout());

		createTable(tableGroup);
		createSpecsButtonsPanel(tableGroup);
