		tree.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleTreeSelectionChanged(event);
			}
		});
