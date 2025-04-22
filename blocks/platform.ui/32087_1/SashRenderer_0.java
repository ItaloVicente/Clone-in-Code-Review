	
	@SuppressWarnings("unchecked")
	@Inject
	@Optional
	private void subscribeTopicOrientationChanged(
			@UIEventTopic(UIEvents.GenericTile.TOPIC_HORIZONTAL) Event event) {
		MUIElement element = (MUIElement) event
				.getProperty(UIEvents.EventTags.ELEMENT);
		if (element.getRenderer() != SashRenderer.this) {
			return;
		}
		forceLayout((MElementContainer<MUIElement>) element);
	}
