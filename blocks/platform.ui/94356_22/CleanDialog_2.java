		alwaysCleanButton = new Button(area, SWT.CHECK);
		alwaysCleanButton.setText(IDEWorkbenchMessages.CleanDialog_alwaysCleanAllButton);
		alwaysCleanButton.setSelection(!settings.getBoolean(TOGGLE_SELECTED));
		alwaysCleanButton.addSelectionListener(widgetSelectedAdapter(e -> {
			updateEnablement();
			if (!alwaysCleanButton.getSelection()) {
				setInitialFilterText();
			} else {
				filterText.setText(""); //$NON-NLS-1$
			}
		}));

		Composite filterTextArea = null;
		if (useNativeSearchField(area)) {
			filterTextArea = new Composite(area, SWT.NONE);
			filterText = new Text(filterTextArea, SWT.BORDER | SWT.SINGLE | SWT.SEARCH | SWT.ICON_CANCEL);
		} else {
			filterTextArea = new Composite(area, SWT.BORDER);
			filterText = new Text(filterTextArea, SWT.SINGLE);
		}

		GridLayout filterTextLayout = new GridLayout();
		filterTextLayout.marginWidth = 0;
		filterTextLayout.marginHeight = 0;
		filterTextLayout.numColumns = 1;
		filterTextLayout.horizontalSpacing = 0;
		filterTextLayout.makeColumnsEqualWidth = false;
		filterTextArea.setLayout(filterTextLayout);
		filterTextArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		filterText.setMessage(IDEWorkbenchMessages.CleanDialog_typeFilterText);
