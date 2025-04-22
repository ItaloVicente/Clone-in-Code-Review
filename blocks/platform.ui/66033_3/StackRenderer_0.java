	private void subscribeTopicTagsChanged(@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {

		if (tabStateHandler == null) {
			tabStateHandler = new TabStateHandler();
		}
		tabStateHandler.handleEvent(event);

