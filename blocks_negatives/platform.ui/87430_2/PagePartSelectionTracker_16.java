    private ISelectionChangedListener postSelectionListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            firePostSelection(getPart(), event.getSelection());
        }
    };
