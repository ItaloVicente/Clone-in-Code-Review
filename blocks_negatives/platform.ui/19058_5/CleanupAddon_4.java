	private EventHandler visibilityChangeHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			if (changedObj instanceof MTrimBar
					|| ((Object) changedObj.getParent()) instanceof MToolBar)
