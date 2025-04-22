		}
	};

	@PostConstruct
	void init(IEclipseContext context) {
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, renderingChangeHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityChangeHandler);
	}

	@PreDestroy
	void removeListeners() {
		eventBroker.unsubscribe(childrenHandler);
		eventBroker.unsubscribe(renderingChangeHandler);
		eventBroker.unsubscribe(visibilityChangeHandler);
