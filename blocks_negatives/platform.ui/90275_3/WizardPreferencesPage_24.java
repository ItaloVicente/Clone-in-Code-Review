		transferAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (transferAllButton.getSelection()) {
					viewer.setAllChecked(false);
				}
				updateEnablement();
				updatePageCompletion();
