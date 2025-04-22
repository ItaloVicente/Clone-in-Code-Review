	private EventHandler selectedHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (!listeners.isEmpty()) {
				Object oldSelected = event.getProperty(UIEvents.EventTags.OLD_VALUE);
				if (oldSelected instanceof MPlaceholder) {
					oldSelected = ((MPlaceholder) oldSelected).getRef();
				}
