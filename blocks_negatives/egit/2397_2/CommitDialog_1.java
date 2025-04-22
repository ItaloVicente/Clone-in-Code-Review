		changeIdButton = new Button(container, SWT.CHECK);
		changeIdButton.setText(UIText.CommitDialog_AddChangeIdLabel);
		changeIdButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		changeIdButton.setToolTipText(UIText.CommitDialog_AddChangeIdTooltip);
		changeIdButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				refreshChangeIdText();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
