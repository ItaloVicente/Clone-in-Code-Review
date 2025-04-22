
		newLabel(g, UIText.RepositorySelectionPage_storeInSecureStore);
		storeCheckbox = new Button(g, SWT.CHECK);
		storeCheckbox.setSelection(storeInSecureStore);
		storeCheckbox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				storeInSecureStore = storeCheckbox.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				storeInSecureStore = storeCheckbox.getSelection();
			}
		});

