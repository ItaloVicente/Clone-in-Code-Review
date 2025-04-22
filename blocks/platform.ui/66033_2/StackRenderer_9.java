	@Inject
	@Optional
	private void subscribeTopicSelectedelementChanged(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		if (tabStateHandler == null) {
			tabStateHandler = new TabStateHandler();
		}
		tabStateHandler.handleEvent(event);
	}
