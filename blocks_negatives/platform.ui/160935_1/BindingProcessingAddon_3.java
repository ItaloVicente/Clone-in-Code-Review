		};
		broker.subscribe(UIEvents.BindingTableContainer.TOPIC_BINDINGTABLES, additionHandler);
		broker.subscribe(UIEvents.BindingTable.TOPIC_BINDINGS, additionHandler);
		broker.subscribe(UIEvents.KeyBinding.TOPIC_COMMAND, additionHandler);
		broker.subscribe(UIEvents.KeyBinding.TOPIC_PARAMETERS, additionHandler);
		broker.subscribe(UIEvents.KeySequence.TOPIC_KEYSEQUENCE, additionHandler);
		broker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, additionHandler);

		contextHandler = event -> {
			Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
