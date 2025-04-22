		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createButton(parent, DETAILS_ID, WorkbenchMessages.AboutDialog_DetailsButton, false);

		Label l = new Label(parent, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = (GridLayout) parent.getLayout();
		layout.numColumns++;
		layout.makeColumnsEqualWidth = false;

		Button b = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		b.setFocus();
	}

	@Override
