	private EventHandler selectedHandler = event -> {
		if (!listeners.isEmpty()) {
			Object oldSelected = event.getProperty(UIEvents.EventTags.OLD_VALUE);
			if (oldSelected instanceof MPlaceholder) {
				oldSelected = ((MPlaceholder) oldSelected).getRef();
			}
