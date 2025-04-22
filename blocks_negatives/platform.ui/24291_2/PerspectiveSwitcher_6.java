		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED,
				toBeRenderedHandler);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectionHandler);
		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL,
				labelHandler);

