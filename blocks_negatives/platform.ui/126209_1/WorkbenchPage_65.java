	private EventHandler referenceRemovalEventHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (Boolean.TRUE.equals(event.getProperty(UIEvents.EventTags.NEW_VALUE))) {
				return;
			}

			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (element instanceof MPlaceholder) {
				MUIElement ref = ((MPlaceholder) element).getRef();
				unzoomSharedArea(ref);
