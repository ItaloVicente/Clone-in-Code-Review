	@Inject
	@Optional
	private void subscribeTopicBinding(@UIEventTopic(UIEvents.BindingTable.TOPIC_BINDINGS) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(elementObj instanceof MBindingTable)) {
			return;
		}

		if (UIEvents.isADD(event)) {
			for (Object newObj : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				if (newObj instanceof MKeyBinding) {
					MKeyBinding binding = (MKeyBinding) newObj;
					updateBinding(binding, true, elementObj);
