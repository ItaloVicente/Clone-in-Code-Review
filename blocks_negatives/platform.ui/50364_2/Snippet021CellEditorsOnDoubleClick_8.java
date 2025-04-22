			@Override
			public void handleEvent(Event event) {
				editingSupport.setEnabled(true);
				TableItem[] selection = table.getSelection();

				if (selection.length != 1) {
					return;
				}
