			projectNames.refresh();
		});

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
