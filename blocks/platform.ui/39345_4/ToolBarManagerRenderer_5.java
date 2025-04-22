	@Inject
	@Optional
	void updateSelection(@UIEventTopic(UIEvents.Item.TOPIC_SELECTED) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement)) {
			return;
		}
