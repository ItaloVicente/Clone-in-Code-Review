
		ignoreErrors = new Button(resourcesTreeComposite, SWT.CHECK);
		ignoreErrors.setText(UIText.CommitDialog_IgnoreErrors);


		ignoreErrors.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.getSource();
				disableCommitButton(button);
			}
		});

		if (!getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)) {
			ignoreErrors.setSelection(true);
		}

