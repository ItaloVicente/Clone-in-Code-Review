		batchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SlowElementAdapter.setBatchFetchedChildren(batchButton.getSelection());
			}
		});
