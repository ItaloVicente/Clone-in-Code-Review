
		if (getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				&& getPreferenceStore()
						.getBoolean(UIPreferences.BLOCK_COMMIT)) {
			ignoreErrors = new Button(resourcesTreeComposite, SWT.CHECK);
			ignoreErrors.setText(UIText.CommitDialog_IgnoreErrors);

			ignoreErrors.setSelection(false);
			ignoreErrors.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.getSource();
					boolean enable = button.getSelection();
					updateMessage();
					updateCommitButtons(enable);
				}
			});
		}

