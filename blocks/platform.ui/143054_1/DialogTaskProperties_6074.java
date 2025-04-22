				}
				markDirty();
			}
		});

		completedCheckbox = new Button(composite, SWT.CHECK);
		completedCheckbox.setText(MarkerMessages.propertiesDialog_completed);
		GridData gridData = new GridData();
		gridData.horizontalIndent = convertHorizontalDLUsToPixels(20);
		completedCheckbox.setLayoutData(gridData);
		completedCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
