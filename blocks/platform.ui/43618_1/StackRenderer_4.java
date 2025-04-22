	}
	
	@Inject
	@Optional
	protected void subscribeTopicDirtyChanged(@UIEventTopic(UIEvents.Dirtyable.TOPIC_ALL) Event event) {
		Object objElement = event
				.getProperty(UIEvents.EventTags.ELEMENT);
