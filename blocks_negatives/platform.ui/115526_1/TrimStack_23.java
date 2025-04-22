	private EventHandler openHandler = new EventHandler() {
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			if (isShowing) {
				return;
			}

			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
