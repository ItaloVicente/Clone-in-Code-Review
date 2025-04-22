		eventBroker.subscribe(
UIEvents.Context.TOPIC_CONTEXT,
				new EventHandler() {
					public void handleEvent(org.osgi.service.event.Event event) {
						Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
						if (element instanceof MPart) {
							MPart part = (MPart) element;
							IEclipseContext context = part.getContext();
							if (context != null) {
								setReference(part, context);
							}
						}
