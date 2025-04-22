	/**
	 * This handler will be notified when the part's widget has been un/set.
	 */
	private EventHandler widgetSetHandler = new EventHandler() {
		public void handleEvent(Event event) {
			if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
					&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
				 Assert.isTrue(!composite.isDisposed(),
				beingDisposed = true;
				WorkbenchPartReference reference = getReference();
				((WorkbenchPage) reference.getPage()).firePartHidden(part);
				((WorkbenchPage) reference.getPage()).firePartClosed(CompatibilityPart.this);
			}
		}
	};

