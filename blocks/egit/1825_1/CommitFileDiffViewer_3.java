		addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				updateActionEnablement(event.getSelection());
			}
		});

