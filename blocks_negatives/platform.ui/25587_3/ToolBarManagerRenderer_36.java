	@Inject
	@Optional
	private void subscribeTopicTagsChanged(
			@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {

		Object changedObj = event.getProperty(EventTags.ELEMENT);

		if (!(changedObj instanceof MToolBar))
			return;

		final MUIElement changedElement = (MUIElement) changedObj;

		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				changedElement.setVisible(false);
				changedElement.setToBeRendered(false);
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				changedElement.setVisible(true);
				changedElement.setToBeRendered(true);
			}
		}
	}

	@Inject
	@Optional
	private void subscribeTopicAppStartup(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
		List<MToolBar> toolBars = modelService.findElements(application, null,
				MToolBar.class, null);
		for (MToolBar mToolBar : toolBars) {
			if (mToolBar.getTags().contains(
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				mToolBar.setVisible(false);
				mToolBar.setToBeRendered(false);
			}
		}
	}

