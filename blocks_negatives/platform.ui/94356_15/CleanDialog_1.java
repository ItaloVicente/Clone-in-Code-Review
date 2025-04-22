		deselectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.TOP, false, false);
		deselectAllButton.setLayoutData(gd);
		deselectAllButton.setText(IDEWorkbenchMessages.CleanDialog_deselectedAllButton);
		deselectAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
			projectNames.setAllChecked(false);
			selection = projectNames.getCheckedElements();
			updateEnablement();
		}));

		alwaysCleanButton = new Button(area, SWT.CHECK);
		alwaysCleanButton.setText(IDEWorkbenchMessages.CleanDialog_alwaysCleanAllButton);
		alwaysCleanButton.setSelection(settings.getBoolean(TOGGLE_SELECTED));
		alwaysCleanButton.addSelectionListener(widgetSelectedAdapter(e -> updateEnablement()));

		new Label(area, SWT.NONE);

