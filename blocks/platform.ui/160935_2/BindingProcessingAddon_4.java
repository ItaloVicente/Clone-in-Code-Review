		}
	}

	@Inject
	@Optional
	private void subscribeTopicCommand(@UIEventTopic(UIEvents.KeyBinding.TOPIC_COMMAND) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(elementObj instanceof MKeyBinding)) {
			return;
		}

		MKeyBinding binding = (MKeyBinding) elementObj;
		Object oldObj = event.getProperty(UIEvents.EventTags.OLD_VALUE);

		MKeyBinding oldBinding = (MKeyBinding) EcoreUtil.copy((EObject) binding);
		oldBinding.setCommand((MCommand) oldObj);
		updateBinding(oldBinding, false, ((EObject) binding).eContainer());
		updateBinding(binding, true, null);
	}

	@Inject
	@Optional
	private void subscribeTopicKeySequence(@UIEventTopic(UIEvents.KeySequence.TOPIC_KEYSEQUENCE) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(elementObj instanceof MKeyBinding)) {
			return;
		}

		MKeyBinding binding = (MKeyBinding) elementObj;
		Object oldObj = event.getProperty(UIEvents.EventTags.OLD_VALUE);

		MKeyBinding oldBinding = (MKeyBinding) EcoreUtil.copy((EObject) binding);
		oldBinding.setKeySequence((String) oldObj);
		updateBinding(oldBinding, false, ((EObject) binding).eContainer());
		updateBinding(binding, true, null);
	}

	@Inject
	@Optional
	private void subscribeTopicParameters(@UIEventTopic(UIEvents.KeyBinding.TOPIC_PARAMETERS) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(elementObj instanceof MKeyBinding)) {
			return;
		}

		MKeyBinding binding = (MKeyBinding) elementObj;

		if (UIEvents.isADD(event)) {
