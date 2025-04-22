		changeIdButton = new Button(container, SWT.CHECK);
		changeIdButton.setText(UIText.CommitDialog_AddChangeId);
		changeIdButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		changeIdButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				createChangeId = changeIdButton.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
