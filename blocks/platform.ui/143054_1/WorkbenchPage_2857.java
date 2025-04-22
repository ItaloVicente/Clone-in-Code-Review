	private EventHandler widgetHandler = event -> {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		if (element instanceof MArea) {
			if (modelService.findElements(window, null, MArea.class).contains(element)) {
				if (newValue instanceof Control) {
					installAreaDropSupport((Control) newValue);
