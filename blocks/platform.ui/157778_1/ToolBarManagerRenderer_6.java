	@Inject
	@Optional
	private void subscribeUIElementTopicVisible(@UIEventTopic(UIEvents.UIElement.TOPIC_VISIBLE) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement)) {
			return;
		}
