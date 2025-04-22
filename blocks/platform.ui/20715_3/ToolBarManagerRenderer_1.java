	@Inject
	@Optional
	void dirtyChanged(
			@UIEventTopic(UIEvents.Dirtyable.TOPIC_DIRTY) Event eventData) {
		updateEnablement();
	}

