UIEvents.UIElement.TOPIC_TOBERENDERED, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				if (Boolean.TRUE.equals(event.getProperty(UIEvents.EventTags.NEW_VALUE))) {
					Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
					if (element instanceof MPart) {
						MPart part = (MPart) element;
						createReference(part);
					}
				}
			}
		});
