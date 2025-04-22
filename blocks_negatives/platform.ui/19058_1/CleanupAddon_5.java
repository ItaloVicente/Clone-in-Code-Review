	};

	private EventHandler renderingChangeHandler = new EventHandler() {
		public void handleEvent(Event event) {
			MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			MElementContainer<MUIElement> container = null;
			if (changedObj.getCurSharedRef() != null)
				container = changedObj.getCurSharedRef().getParent();
			else
				container = changedObj.getParent();

			if (container == null) {
				return;
			}
