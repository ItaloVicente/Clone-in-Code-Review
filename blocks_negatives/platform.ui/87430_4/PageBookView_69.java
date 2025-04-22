	private ISelectionChangedListener selectionChangedListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			pageSelectionChanged(event);
		}
	};
