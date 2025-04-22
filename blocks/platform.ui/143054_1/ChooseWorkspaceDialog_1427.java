	private void createWorkspaceBrowseRow(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		panel.setFont(parent.getFont());

		CLabel label = new CLabel(panel, SWT.NONE);
		label.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_workspaceEntryLabel);
		label.setMargins(0, 0, 2, 0);

		text = new Combo(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		new DirectoryProposalContentAssist().apply(text);
		text.setTextDirection(SWT.AUTO_TEXT_DIRECTION);
		text.setFocus();
		text.setLayoutData(new GridData(400, SWT.DEFAULT));
		text.addModifyListener(e -> {
