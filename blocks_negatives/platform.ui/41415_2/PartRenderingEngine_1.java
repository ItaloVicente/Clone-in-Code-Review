	private EventHandler visibilityHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			MUIElement parent = changedElement.getParent();
			if (parent == null) {
				parent = (MUIElement) ((EObject) changedElement).eContainer();
				if (parent == null) {
					return;
				}
			}
