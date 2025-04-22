	private EventHandler closeHandler = new EventHandler() {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			if (!isShowing) {
				return;
			}
