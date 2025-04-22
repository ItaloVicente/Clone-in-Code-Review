		signedOffItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				commitMessageComponent
						.setSignedOffButtonSelection(signedOffItem
								.getSelection());
			}
		});

		commitMessageComponent.updateUI();
		commitMessageComponent.enableListers(true);

		return messageAndPersonArea;
