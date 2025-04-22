	@PostConstruct
	void hookListeners() {
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, widgetListener);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, perspectiveRemovedListener);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT,
				perspectiveChangeListener);
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagChangeListener);
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_ELEMENTID, idChangeListener);

		eventBroker.subscribe(UIEvents.UILifeCycle.PERSPECTIVE_SAVED, perspSavedListener);
		eventBroker.subscribe(UIEvents.UILifeCycle.PERSPECTIVE_OPENED, perspOpenedListener);
