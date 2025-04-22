	@Inject
	@Optional
	private void subscribeBindingTableContainerTopicBindingTables(
			@UIEventTopic(UIEvents.BindingTableContainer.TOPIC_BINDINGTABLES) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(elementObj instanceof MApplication)) {
			return;
		}
