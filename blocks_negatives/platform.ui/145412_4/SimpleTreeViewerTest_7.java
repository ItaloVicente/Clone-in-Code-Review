		final ISelectionChangedListener listener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				fail();
			}
		};
