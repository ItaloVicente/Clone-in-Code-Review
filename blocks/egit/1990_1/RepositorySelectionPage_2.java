
		newLabel(g, "Store in Secure Store"); //$NON-NLS-1$ // TODO
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

