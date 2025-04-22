	};

	@PostConstruct
	void hookListeners() {
		String topic = UIEvents.UIElement.TOPIC_WIDGET;
		eventBroker.subscribe(topic, null, installHook, false);
