	@Inject
	@Optional
	private void subscribeTopicSelectedelementChanged(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		TabStateHandler tabStateHandler = new TabStateHandler();
		tabStateHandler.handleEvent(event);
	}
