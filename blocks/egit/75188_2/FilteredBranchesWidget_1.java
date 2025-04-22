		column.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSortedColumn(column, labelProvider);
			}
		});
