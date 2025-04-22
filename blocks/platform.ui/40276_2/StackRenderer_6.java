	@Inject
	@Optional
	private void subscribeTopicSelectedElementChanged(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		tabStateHandler.handleEvent(event);
	}
