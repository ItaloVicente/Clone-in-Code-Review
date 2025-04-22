	@Inject
	@Optional
	private void subscribeRenderingChanged(
			@UIEventTopic(UIEvents.UIElement.TOPIC_TOBERENDERED) Event event) {
		MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		MElementContainer<MUIElement> container = null;
		if (changedObj.getCurSharedRef() != null)
			container = changedObj.getCurSharedRef().getParent();
		else
			container = changedObj.getParent();

		if (container == null) {
			return;
		}

		MUIElement containerElement = container;
		if (containerElement instanceof MWindow && containerElement.getParent() != null) {
			return;
		}
