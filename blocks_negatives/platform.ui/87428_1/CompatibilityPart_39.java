	private EventHandler widgetSetHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
					&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
				 Assert.isTrue(!composite.isDisposed(),
				beingDisposed = true;
				WorkbenchPartReference reference = getReference();
				((WorkbenchPage) reference.getPage()).firePartDeactivatedIfActive(part);
				((WorkbenchPage) reference.getPage()).firePartHidden(part);
				((WorkbenchPage) reference.getPage()).firePartClosed(CompatibilityPart.this);
			}
