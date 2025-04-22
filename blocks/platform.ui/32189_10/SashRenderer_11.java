	@SuppressWarnings("unchecked")
	@Inject
	@Optional
	private void subscribeTopicSizeInfoChanged(
			@UIEventTopic(ApplicationElement.TOPIC_TRANSIENTDATA) Event event) {
		MUIElement element = (MUIElement) event
				.getProperty(UIEvents.EventTags.ELEMENT);
		if (element.getRenderer() != SashRenderer.this) {
			return;
		}
		forceLayout((MElementContainer<MUIElement>) element);
	}

