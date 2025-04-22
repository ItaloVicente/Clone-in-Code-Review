    private ISelectionChangedListener focusSelectionChangedListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            TaskList.this.focusSelectionChanged(event);
        }
    };
