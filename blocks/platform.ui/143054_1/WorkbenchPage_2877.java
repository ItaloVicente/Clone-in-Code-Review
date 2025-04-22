	private EventHandler firingHandler = event -> {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object value = event.getProperty(UIEvents.EventTags.NEW_VALUE);
		if (value instanceof CompatibilityPart && element instanceof MPart) {
			Integer events = partEvents.remove(element);
			if (events != null) {
				int e = events.intValue();
				if ((e & FIRE_PART_VISIBLE) == FIRE_PART_VISIBLE) {
					firePartVisible((MPart) element);
				}
				if ((e & FIRE_PART_BROUGHTTOTOP) == FIRE_PART_BROUGHTTOTOP) {
					firePartBroughtToTop((MPart) element);
