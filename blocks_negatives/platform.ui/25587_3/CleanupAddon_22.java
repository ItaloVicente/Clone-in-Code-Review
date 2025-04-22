	@Inject
	@Optional
	private void subscribeTopicChildren(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_CHILDREN) Event event) {
		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (UIEvents.isREMOVE(event)) {
			final MElementContainer<?> container = (MElementContainer<?>) changedObj;
			MUIElement containerParent = container.getParent();

			if (container instanceof MApplication || container instanceof MPerspectiveStack
					|| container instanceof MMenuElement || container instanceof MTrimBar
					|| container instanceof MToolBar || container instanceof MArea
					|| container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
				return;
			}

			if (container instanceof MWindow && containerParent instanceof MApplication) {
				return;
			}
