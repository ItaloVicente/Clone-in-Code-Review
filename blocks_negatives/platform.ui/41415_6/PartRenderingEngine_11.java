	private EventHandler windowsHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			childrenHandler.handleEvent(event);
		}
	};
