		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createNewWorkingSet(newButton.getShell());
			}
		});
