		separateModeButton = new Button(main, SWT.CHECK);
		separateModeButton
				.setText(UIText.ExistingOrNewPage_SeparateModeCheckbox);
		separateModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (getInternalMode() && getSeparateMode()) {
					internalModeButton.setSelection(false);
				}
				updateControls();
			}
		});
		if (myWizard.projects.length > 1) {
			separateModeButton.setEnabled(false);
		}

