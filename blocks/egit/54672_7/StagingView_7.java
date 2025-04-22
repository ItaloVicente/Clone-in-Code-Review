		ignoreErrors = toolkit.createButton(buttonsContainer,
					UIText.StagingView_IgnoreErrors, SWT.CHECK);
		ignoreErrors.setSelection(false);
		ignoreErrors.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateMessage();
				final IndexDiffData indexDiff = doReload(currentRepository);
				boolean indexDiffAvailable = indexDiffAvailable(indexDiff);
				boolean noConflicts = noConflicts(indexDiff);
				boolean hasErrorsOrWarnings = hasErrorsOrWarnings();

				boolean commitEnabled = commitEnabled(indexDiffAvailable,
						noConflicts, hasErrorsOrWarnings);

				boolean commitAndPushEnabled = commitAndPushEnabled(
						commitEnabled);

				commitButton.setEnabled(commitEnabled);
				commitAndPushButton.setEnabled(commitAndPushEnabled);
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
						final IndexDiffData indexDiff = doReload(
								currentRepository);
						boolean indexDiffAvailable = indexDiffAvailable(
								indexDiff);
						boolean noConflicts = noConflicts(indexDiff);
						boolean hasErrorsOrWarnings = hasErrorsOrWarnings();

						boolean commitEnabled = commitEnabled(indexDiffAvailable, noConflicts,
								hasErrorsOrWarnings);

						boolean commitAndPushEnabled = commitAndPushEnabled(commitEnabled);

						commitButton.setEnabled(commitEnabled);
						commitAndPushButton.setEnabled(commitAndPushEnabled);
					}
				});

		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(true, true).applyTo(ignoreErrors);
		updateIgnoreErrorsButtonVisibility();

