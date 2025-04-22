		lightweightButton = new Button(left, SWT.CHECK);
		lightweightButton.setEnabled(true);
		lightweightButton.setText(UIText.CreateTagDialog_lightweightTag);
		lightweightButton
				.setToolTipText(UIText.CreateTagDialog_lightweightTagToolTip);
		lightweightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validateInput();
			}
		});

