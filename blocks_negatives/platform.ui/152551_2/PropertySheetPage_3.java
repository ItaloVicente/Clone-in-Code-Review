		selectionChangeListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleEntrySelection(event.getSelection());
			}
		};
