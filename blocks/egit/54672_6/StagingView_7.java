		ignoreErrors = toolkit.createButton(buttonsContainer,
					UIText.StagingView_IgnoreErrors, SWT.CHECK);
		ignoreErrors.setSelection(false);
		ignoreErrors.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateMessage();
				boolean enabled = !hasErrorsOrWarnings();
				commitButton.setEnabled(enabled);
				commitAndPushButton.setEnabled(enabled);
			}
		});
		getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (isDisposed()) {
							getPreferenceStore()
									.removePropertyChangeListener(this);
						}
						updateIgnoreErrorsButtonVisibility();
						updateMessage();
						boolean enabled = !hasErrorsOrWarnings();
						commitButton.setEnabled(enabled);
						commitAndPushButton.setEnabled(enabled);
					}
				});
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(true, true).applyTo(ignoreErrors);
		updateIgnoreErrorsButtonVisibility();

