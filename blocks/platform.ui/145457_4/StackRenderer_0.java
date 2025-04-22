	@Inject
	@Optional
	void subscribeTopicIconChanged(@UIEventTopic(UIEvents.UILabel.TOPIC_ICONURI) Event event) {
		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (changedObj instanceof MPart) {
			MPart part = (MPart) changedObj;
			Map<String, Object> transientData = part.getTransientData();
			if (transientData.containsKey(ICON_URI_FOR_PART)) {
				part.getTransientData().remove(ICON_URI_FOR_PART);
			}
		}
	}

