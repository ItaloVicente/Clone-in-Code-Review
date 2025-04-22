	private EventHandler zoomChangeListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (!handleZoomEvents)
				return;

			Object changedObj = event.getProperty(EventTags.ELEMENT);
			if (!(changedObj instanceof MPartStack))
				return;

			if (changedObj != getIntroStack())
				return;

			if (UIEvents.isADD(event)
					&& UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
							IPresentationEngine.MAXIMIZED)) {
				setStandby(false);
			} else if (UIEvents.isREMOVE(event)
					&& UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
							IPresentationEngine.MAXIMIZED)) {
				setStandby(true);
			}
