	private EventHandler toBeRenderedHandler = new EventHandler() {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			if (minimizedElement == null || trimStackTB == null) {
				return;
			}
