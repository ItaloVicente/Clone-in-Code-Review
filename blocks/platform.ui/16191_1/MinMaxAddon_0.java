	@Inject
	@Optional
	private void subscribeTopicWidget(@UIEventTopic(UIEvents.UIElement.TOPIC_WIDGET) Event event) {
		final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
		if (!(changedElement instanceof MPartStack) && !(changedElement instanceof MArea))
			return;
