	private EventHandler childAdditionUpdater = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBar))
				return;
			MToolBar toolbarModel = (MToolBar) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			if (UIEvents.isADD(event)) {
				Object obj = toolbarModel;
				processContents((MElementContainer<MUIElement>) obj);
			}
