	private EventHandler referenceRemovalEventHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (Boolean.TRUE.equals(event.getProperty(UIEvents.EventTags.NEW_VALUE))) {
				return;
			}
