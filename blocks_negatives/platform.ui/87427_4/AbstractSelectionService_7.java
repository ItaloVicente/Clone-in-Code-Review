    private ISelectionChangedListener postSelListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            firePostSelection(activePart, event.getSelection());
        }
    };
