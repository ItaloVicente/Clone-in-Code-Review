		}
	};

	private EventHandler selectionUpdater = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement))
