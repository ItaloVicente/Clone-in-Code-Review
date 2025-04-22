			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateTableSorting(columnIndex);
				}
			});
