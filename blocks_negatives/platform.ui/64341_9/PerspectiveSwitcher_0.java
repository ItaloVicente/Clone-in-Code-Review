	void handleChildrenEvent(@Optional @UIEventTopic(UIEvents.ElementContainer.TOPIC_CHILDREN) Event event) {

		if (event == null)
			return;

		if (perspSwitcherToolbar.isDisposed()) {
			return;
		}
