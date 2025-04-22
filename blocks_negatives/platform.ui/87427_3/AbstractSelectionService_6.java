    private ISelectionChangedListener selListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            fireSelection(activePart, event.getSelection());
        }
    };
