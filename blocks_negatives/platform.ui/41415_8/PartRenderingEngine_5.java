	private EventHandler trimHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(changedObj instanceof MTrimmedWindow))
				return;
