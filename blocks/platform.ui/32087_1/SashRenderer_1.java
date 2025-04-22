	@SuppressWarnings("unchecked")
	@Inject
	@Optional
	private void subscribeTopicSashWeightChanged(
			@UIEventTopic(UIEvents.UIElement.TOPIC_CONTAINERDATA) Event event) {
		MUIElement element = (MUIElement) event
				.getProperty(UIEvents.EventTags.ELEMENT);
		if (element.getRenderer() != SashRenderer.this) {
			return;
		}
		forceLayout((MElementContainer<MUIElement>) element);
