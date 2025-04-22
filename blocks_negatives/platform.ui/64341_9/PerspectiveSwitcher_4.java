	void handleSelectionEvent(@Optional @UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		if (event == null)
			return;
		if (perspSwitcherToolbar.isDisposed()) {
			return;
		}
