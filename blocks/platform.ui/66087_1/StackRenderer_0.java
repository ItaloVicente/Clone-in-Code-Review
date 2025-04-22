		updatePartTab(event, part);
	}

	@Inject
	@Optional
	private void subscribeTopicClosablePartChanged(@UIEventTopic(UIEvents.Part.TOPIC_CLOSEABLE) Event event) {
		updateClosableTab(event);
	}

	@Inject
	@Optional
	private void subscribeTopicClosablePlaceholderChanged(
			@UIEventTopic(UIEvents.Placeholder.TOPIC_CLOSEABLE) Event event) {
		updateClosableTab(event);
	}

	private void updateClosableTab(Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

		MPart part = null;
		if (element instanceof MPart) {
			part = (MPart) element;
		} else if (element instanceof MPlaceholder) {
			MUIElement ref = ((MPlaceholder) element).getRef();
			if (ref instanceof MPart) {
				part = (MPart) ref;
			}
		}

		if (part == null) {
			return;
		}

		updatePartTab(event, part);
	}

	private void updatePartTab(Event event, MPart part) {
