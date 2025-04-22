	private EventHandler labelHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (psTB.isDisposed()) {
				return;
			}
