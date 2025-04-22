	@PostConstruct
	void init() {
		eventBroker.subscribe(UIElement.TOPIC_WIDGET, widgetListener);
	}

	@PreDestroy
	void contextDisposed() {
		eventBroker.unsubscribe(widgetListener);
