	IEventBroker eventBroker;

	private EventHandler installHook = new EventHandler() {
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!(changedElement instanceof MWindow))
				return;

			Widget widget = (Widget) event.getProperty(EventTags.NEW_VALUE);
			if (widget instanceof Shell && !widget.isDisposed()) {
				if (theManager == null) {
					theManager = new DnDManager((MWindow) changedElement);
					widget.setData("DnDManager", theManager); //$NON-NLS-1$
				}
