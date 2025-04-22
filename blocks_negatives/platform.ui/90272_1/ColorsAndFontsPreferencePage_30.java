		expandAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				tree.getViewer().expandAll();
			}
		});
