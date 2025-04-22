    private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            fireSelection(getPart(), event.getSelection());
        }
    };
