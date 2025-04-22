	@Inject
	@Optional
	private void subscribeTopicTagsChanged(
			@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {

		Object changedObj = event.getProperty(EventTags.ELEMENT);

		if (!(changedObj instanceof MToolBar)
				|| !(changedObj instanceof MToolBar))
			return;

		final MUIElement changedElement = (MUIElement) changedObj;

		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
					IPresentationEngine.HIDDEN_BY_USER)) {
				changedElement.setVisible(false);
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					IPresentationEngine.HIDDEN_BY_USER)) {
				changedElement.setVisible(true);
			}
		}
	}


