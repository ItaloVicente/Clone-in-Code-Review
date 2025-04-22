	private ISelectionChangedListener statusBarListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent anEvent) {
			updateStatusBar(anEvent.getSelection());
		}

	};
