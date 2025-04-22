	@Inject
	@Optional
	public void subscribeTopicWidget(@Named(UIEvents.UIElement.TOPIC_WIDGET) Event event) {
		MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
		if (!(changedElement instanceof MWindow))
			return;

		Widget widget = (Widget) event.getProperty(EventTags.NEW_VALUE);
		if (widget instanceof Shell && !widget.isDisposed()) {
			DnDManager theManager = (DnDManager) widget.getData("DnDManager"); //$NON-NLS-1$
			if (theManager == null) {
				theManager = new DnDManager((MWindow) changedElement);
				widget.setData("DnDManager", theManager); //$NON-NLS-1$
