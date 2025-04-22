		deselectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.TOP, false, false);
		deselectAllButton.setLayoutData(gd);
		deselectAllButton.setText(IDEWorkbenchMessages.CleanDialog_deselectedAllButton);
		deselectAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
			projectNames.setAllChecked(false);
			selection = projectNames.getCheckedElements();
			updateEnablement();
		}));
