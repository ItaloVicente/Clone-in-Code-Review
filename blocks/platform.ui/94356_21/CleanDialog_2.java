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
			filterTextArea.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			filterText = new Text(filterTextArea, SWT.SINGLE);
		}

		layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = false;
		filterTextArea.setLayout(layout);
		filterTextArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		filterText.setMessage(IDEWorkbenchMessages.CleanDialog_typeFilterText);
