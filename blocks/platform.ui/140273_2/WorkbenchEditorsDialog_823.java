		allSelection.setFont(font);
		setButtonLayoutData(allSelection);

		Composite actionButtons = new Composite(dialogArea, SWT.NULL);
		Label actLabel = new Label(actionButtons, SWT.NULL);
		actLabel.setFont(font);
		GridLayout actLayout = new GridLayout();
		actLayout.numColumns = 4;
		actionButtons.setLayout(actLayout);

		createButton(actionButtons, IDialogConstants.OK_ID, WorkbenchMessages.WorkbenchEditorsDialog_activate, true);

		closeSelected = new Button(actionButtons, SWT.PUSH);
		closeSelected.setText(WorkbenchMessages.WorkbenchEditorsDialog_closeSelected);
		closeSelected.addSelectionListener(widgetSelectedAdapter(e -> closeItems(editorsTable.getSelection())));
		closeSelected.setFont(font);
		setButtonLayoutData(closeSelected);

		saveSelected = new Button(actionButtons, SWT.PUSH);
		saveSelected.setText(WorkbenchMessages.WorkbenchEditorsDialog_saveSelected);
		saveSelected.addSelectionListener(widgetSelectedAdapter(e -> saveItems(editorsTable.getSelection())));
		saveSelected.setFont(font);
		setButtonLayoutData(saveSelected);

		final Button showAllPerspButton = new Button(dialogArea, SWT.CHECK);
		showAllPerspButton.setText(WorkbenchMessages.WorkbenchEditorsDialog_showAllPersp);
		showAllPerspButton.setSelection(showAllPersp);
		showAllPerspButton.setFont(font);
		setButtonLayoutData(showAllPerspButton);
		showAllPerspButton.addSelectionListener(widgetSelectedAdapter(e -> {
			showAllPersp = showAllPerspButton.getSelection();
			updateItems();
