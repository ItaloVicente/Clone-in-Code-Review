	@Inject
	@Optional
	private void subscribeStyleChanged(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event) {
		MUIElement changed = (MUIElement) event
				.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changed instanceof MPart))
			return;
