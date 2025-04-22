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

		Composite filterTextArea = new Composite(area, SWT.NONE);
		layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = false;
		filterTextArea.setLayout(layout);
		filterTextArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		filterText = new Text(filterTextArea, SWT.SEARCH | SWT.ICON_CANCEL);
		filterText.setMessage(IDEWorkbenchMessages.CleanDialog_typeFilterText);
