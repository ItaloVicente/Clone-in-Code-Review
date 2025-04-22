		selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (selectionService != null)
					selectionService.setSelection(event.getSelection());
			}
