	private ISelectionChangedListener postSelectionListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			postSelectionChanged(event);
		}
	};
