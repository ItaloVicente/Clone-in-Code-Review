		serializeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SlowElementAdapter.setSerializeFetching(serializeButton.getSelection());
			}
		});
