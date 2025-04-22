		actionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actionButton.setEnabled(false);
				cancelOrRemove();
			}
		});
