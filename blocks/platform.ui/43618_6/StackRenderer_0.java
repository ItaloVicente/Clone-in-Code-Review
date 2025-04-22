	@Inject
	@Optional
	private void handleTabActivation(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event) {
		MUIElement changed = (MUIElement) event
				.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changed instanceof MPart))
			return;
