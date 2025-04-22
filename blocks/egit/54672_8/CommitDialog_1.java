
		if (getPreferenceStore()
				.getBoolean(UIPreferences.CHECK_BEFORE_COMMITTING)) {
			ignoreErrors = new Button(resourcesTreeComposite, SWT.CHECK);
			ignoreErrors.setText(UIText.CommitDialog_IgnoreErrors);

			ignoreErrors.setSelection(false);
			ignoreErrors.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.getSource();
					boolean enable = button.getSelection()
							|| getProblemsSeverity() < IMarker.SEVERITY_WARNING;
					updateCommitButtons(enable);
					updateMessage();
				}
			});
		}

