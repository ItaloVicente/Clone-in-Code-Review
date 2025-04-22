	@Inject
	@Optional
	private void subscribeAppElemTopicTags(@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(elementObj instanceof MKeyBinding)) {
			return;
		}

		MKeyBinding binding = (MKeyBinding) elementObj;

		List<String> tags = binding.getTags();
		if (tags.contains(EBindingService.DELETED_BINDING_TAG)) {
			updateBinding(binding, false, elementObj);
		}
		else {
			updateBinding(binding, true, elementObj);
		}
