		ignoreErrors = toolkit.createButton(buttonsContainer,
				UIText.StagingView_IgnoreErrors,
				SWT.CHECK);
		if (!getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)) {
			ignoreErrors.setSelection(true);
		}
		ignoreErrors.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.getSource();
				if (!button.getSelection()
						&& getProblemsSeverity() >= IMarker.SEVERITY_WARNING) {
					commitAndPushButton.setEnabled(false);
					commitButton.setEnabled(false);
				} else {
					commitAndPushButton.setEnabled(true);
					commitButton.setEnabled(true);
				}
			}
		});
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(true, true)
				.applyTo(ignoreErrors);

