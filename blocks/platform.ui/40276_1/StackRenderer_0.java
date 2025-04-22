	@Inject
	@Optional
	private void subscribeTopicAllChanged(@UIEventTopic(UIEvents.UILabel.TOPIC_ALL) Event event) {
		MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(element instanceof MPart))
			return;
