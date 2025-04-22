		super.createButtonsForButtonBar(parent);
		createDetailsButton(parent);
	}

	protected void createSpacer(Composite parent) {
		Label spacer = new Label(parent, SWT.NONE);
		spacer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL));
	}

	protected void createDetailsButton(Composite parent) {
		detailsButton = createButton(parent, IDialogConstants.DETAILS_ID,
				ProgressMessages.ProgressMonitorJobsDialog_DetailsTitle,
				false);
		detailsButton.addSelectionListener(new SelectionAdapter() {
			@Override
