	@Inject
	@Optional
	void subscribeTopicIconChanged(@UIEventTopic(UIEvents.UILabel.TOPIC_ICONURI) Event event) {
		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);

		if (changedObj instanceof MPart) {
			updateIconUri((MPart) changedObj);
		}
	}

