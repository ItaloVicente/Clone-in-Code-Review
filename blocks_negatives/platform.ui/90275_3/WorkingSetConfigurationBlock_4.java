		enableButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateEnableState(enableButton.getSelection());
			}
		});
