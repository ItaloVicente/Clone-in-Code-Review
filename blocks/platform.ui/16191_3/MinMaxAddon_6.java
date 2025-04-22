
	@Inject
	@Optional
	private void subscribeTopicTagsChanged(
			@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		if (ignoreTagChanges)
			return;

		Object changedObj = event.getProperty(EventTags.ELEMENT);

		if (!(changedObj instanceof MUIElement))
			return;

		final MUIElement changedElement = (MUIElement) changedObj;

		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE, MINIMIZED)) {
				minimize(changedElement);
			} else if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE, MAXIMIZED)) {
				maximize(changedElement);
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE, MINIMIZED)) {
				restore(changedElement);
			} else if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE, MAXIMIZED)) {
				unzoom(changedElement);
