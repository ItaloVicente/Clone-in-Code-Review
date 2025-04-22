	@Inject
	void handleLabelEvent(@Optional @UIEventTopic(UIEvents.UILabel.TOPIC_ALL) Event event) {
		if (event == null)
			return;
		if (psTB.isDisposed()) {
			return;
		}
