	void handleLabelEvent(@Optional @UIEventTopic(UIEvents.UILabel.TOPIC_ALL) Event event) {
		if (event == null)
			return;
		if (perspSwitcherToolbar.isDisposed()) {
			return;
		}

		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
