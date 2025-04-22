	private EventHandler objectSetHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
					&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
				WorkbenchPartReference reference = getReference();
				((WorkbenchPage) reference.getPage()).firePartOpened(CompatibilityPart.this);
			}
