		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(element instanceof MApplication)) {
					return;
				}
				MApplication app = (MApplication) element;
				if (UIEvents.isREMOVE(event)) {
					if (app.getChildren().isEmpty()) {
						Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);
								+ " was just removed", new Exception()); //$NON-NLS-1$
					}
