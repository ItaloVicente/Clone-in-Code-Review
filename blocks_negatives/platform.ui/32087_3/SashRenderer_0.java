	@PostConstruct
	void postConstruct() {
		sashOrientationHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				MUIElement element = (MUIElement) event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (element.getRenderer() != SashRenderer.this) {
					return;
				}
				forceLayout((MElementContainer<MUIElement>) element);
			}
		};

		eventBroker.subscribe(UIEvents.GenericTile.TOPIC_HORIZONTAL,
				sashOrientationHandler);

		sashWeightHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				MUIElement element = (MUIElement) event
						.getProperty(UIEvents.EventTags.ELEMENT);
				MElementContainer<MUIElement> parent = element.getParent();
				if (parent.getRenderer() != SashRenderer.this)
					return;

				forceLayout(parent);
			}
		};
