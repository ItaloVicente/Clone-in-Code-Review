	@Inject
	@Optional
	void subscribeTopicDirtyChanged(@UIEventTopic(UIEvents.Dirtyable.TOPIC_DIRTY) Event event) {
		Object objElement = event.getProperty(UIEvents.EventTags.ELEMENT);

		if (!(objElement instanceof MPart)) {
			return;
		}
		MPart part = (MPart) objElement;
		Object wrapperPart = part.getTransientData().get(E4PartWrapper.E4_WRAPPER_KEY);
		if (wrapperPart instanceof E4PartWrapper) {
			E4PartWrapper wrapper = (E4PartWrapper) wrapperPart;
			try {
				wrapper.addPropertyListener(AbstractSaveHandler.getDirtyStateTracker());
			} catch (IllegalArgumentException e) {
			}
			wrapper.notifyPartDirtyStatus();
		}
	}

