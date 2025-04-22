	private EventHandler shutdownHandler = new EventHandler() {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			showStack(false);
		}
	};
