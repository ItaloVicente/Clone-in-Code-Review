		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL, itemUpdater);
		eventBroker.subscribe(UIEvents.Item.TOPIC_SELECTED, selectionUpdater);
		eventBroker.subscribe(UIEvents.Item.TOPIC_ENABLED, enabledUpdater);
		eventBroker
				.subscribe(UIEvents.UIElement.TOPIC_ALL, toBeRenderedUpdater);
		eventBroker.subscribe(ElementContainer.TOPIC_CHILDREN,
				childAdditionUpdater);

