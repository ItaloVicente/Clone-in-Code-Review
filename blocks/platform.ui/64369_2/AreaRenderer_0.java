	@Inject
	@Optional
	private void handleTopicWidgets(@UIEventTopic(UIElement.TOPIC_WIDGET) Event event)
	{
		final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
		if (!(changedElement instanceof MPartStack))
			return;
