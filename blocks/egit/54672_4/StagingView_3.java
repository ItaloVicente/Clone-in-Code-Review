		if (getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)) {
			ignoreErrors = toolkit.createButton(buttonsContainer,
					UIText.StagingView_IgnoreErrors, SWT.CHECK);
			ignoreErrors.setSelection(true);
			ignoreErrors.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.getSource();
					if (!button.getSelection()
							&& getProblemsSeverity() >= IMarker.SEVERITY_WARNING) {
						commitAndPushButton.setEnabled(false);
						commitButton.setEnabled(false);
						warningLabel
								.showMessage(UIText.StagingView_MessageErrors);
					} else {
						commitAndPushButton.setEnabled(true);
						commitButton.setEnabled(true);
						warningLabel.hideMessage();
					}
				}
			});
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
					.grab(true, true).applyTo(ignoreErrors);
		}

