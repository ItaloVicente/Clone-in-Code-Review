

		if (getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)
				&& getProblemsSeverity() >= IMarker.SEVERITY_WARNING) {
			ignoreErrors = new Button(resourcesTreeComposite, SWT.CHECK);
			ignoreErrors.setText(UIText.CommitDialog_IgnoreErrors);

			ignoreErrors.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.getSource();
					disableCommitButton(button);
					setMessage(UIText.CommitDialog_MessageErrors,
							IMessageProvider.WARNING);
				}
			});

			ignoreErrors.setSelection(true);
		}

