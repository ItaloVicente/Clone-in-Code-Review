	
	private EventHandler initializeStylingHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (stylingPreferencesHandler != null) {
				stylingPreferencesHandler.handleEvent(event);
			}
			eventBroker.unsubscribe(this);
		}
	};
	
