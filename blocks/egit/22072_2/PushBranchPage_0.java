		final Button forceUpdateButton = new Button(inputPanel, SWT.CHECK);
		forceUpdateButton.setText(UIText.PushBranchPage_ForceUpdateButton);
		forceUpdateButton.setSelection(false);
		forceUpdateButton.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).span(3, 1).create());
		forceUpdateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				forceUpdateSelected = forceUpdateButton.getSelection();
			}
		});

