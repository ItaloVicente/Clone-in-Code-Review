		signCommitItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setSignCommitButtonSelection(
						signCommitItem.getSelection());
			}
		});

