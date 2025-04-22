	private ISelectionChangedListener postListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent e) {
			ESelectionService selectionService = part.getContext().get(ESelectionService.class);
			selectionService.setPostSelection(e.getSelection());
		}
