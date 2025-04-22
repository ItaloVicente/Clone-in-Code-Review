	private EventHandler selectionHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (psTB.isDisposed()) {
				return;
			}
