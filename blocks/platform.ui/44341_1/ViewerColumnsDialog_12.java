		nonVisibleViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleNonVisibleSelection(event.getSelection());
			}
		});
