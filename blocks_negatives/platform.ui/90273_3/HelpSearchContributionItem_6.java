		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = combo.getSelectionIndex();
				if (index != -1) {
					doSearch(combo.getItem(index), false);
				}
