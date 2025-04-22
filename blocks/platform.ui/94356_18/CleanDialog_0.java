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

