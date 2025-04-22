	private EventHandler widgetHandler = new EventHandler() {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (changedObj != minimizedElement) {
				return;
			}
