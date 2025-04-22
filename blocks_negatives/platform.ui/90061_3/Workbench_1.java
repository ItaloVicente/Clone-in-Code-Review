		eventBroker.subscribe(
UIEvents.Context.TOPIC_CONTEXT,
				event -> {
					Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
					if (element instanceof MPart) {
						MPart part = (MPart) element;
						IEclipseContext context = part.getContext();
						if (context != null) {
							setReference(part, context);
						}
					}
				});
