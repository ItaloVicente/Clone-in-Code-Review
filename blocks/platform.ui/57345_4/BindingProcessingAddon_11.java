		contextHandler = event -> {
			Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			Object newObj = event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (UIEvents.EventTypes.SET.equals(event.getProperty(UIEvents.EventTags.TYPE))
					&& newObj instanceof IEclipseContext) {
				activateContexts(elementObj);
