	private EventHandler eventHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (element instanceof MPart) {
				MPart part = (MPart) element;

				String partId = part.getElementId();
				if (targetedListeners.containsKey(partId)
						|| targetedPostListeners.containsKey(partId))
					track(part);
			}
