	protected void createRenameModeGroup(Composite composite) {

		Group buttonComposite = new Group(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		buttonComposite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		buttonComposite.setLayoutData(data);
		buttonComposite.setText(WorkbenchMessages.WorkbenchPreference_renameMode);

		String label = WorkbenchMessages.WorkbenchPreference_renameModeInline;
		renameModeInlineButton = createRadioButton(buttonComposite, label);
		renameModeInlineButton.addSelectionListener(
				widgetSelectedAdapter(e -> renameModeInline = renameModeInlineButton.getSelection()));
		renameModeInlineButton.setSelection(renameModeInline);

		label = WorkbenchMessages.WorkbenchPreference_renameModeDialog;
		renameModeDialogButton = createRadioButton(buttonComposite, label);
		renameModeDialogButton.addSelectionListener(
				widgetSelectedAdapter(e -> renameModeInline = !renameModeDialogButton.getSelection()));
		renameModeDialogButton.setSelection(!renameModeInline);
	}

