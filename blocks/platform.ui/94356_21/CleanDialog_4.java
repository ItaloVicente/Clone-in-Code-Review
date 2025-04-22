		filterText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (filterText.getText().equals(IDEWorkbenchMessages.CleanDialog_typeFilterText)) {
					filterText.setText(""); //$NON-NLS-1$
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		createClearTextNew(filterTextArea);
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(false);
		}

		createProjectSelectionTable(area);
		if (!alwaysCleanButton.getSelection()) {
			setInitialFilterText();
		}
