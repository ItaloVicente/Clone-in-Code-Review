		}
	}

	@Inject
	@Optional
	private void monitorApplicationTagChanges(@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow) {
			MWindow window = (MWindow) event.getProperty(UIEvents.EventTags.ELEMENT);
			updateFullScreenStatus(window);
		}
