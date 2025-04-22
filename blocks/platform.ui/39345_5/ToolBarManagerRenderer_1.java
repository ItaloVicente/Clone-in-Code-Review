	@Inject
	@Optional
	private void subscribeTopicUpdateToBeRendered(@UIEventTopic(UIEvents.UIElement.TOPIC_ALL) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement)) {
			return;
		}
