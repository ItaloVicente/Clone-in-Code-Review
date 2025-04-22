
	@Inject
	@Optional
	private void subscribeContextTopicContext(@UIEventTopic(UIEvents.Context.TOPIC_CONTEXT) Event event) {
		Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object newObj = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		if (!(newObj instanceof IEclipseContext)) {
			return;
		}

		activateContexts(elementObj);
	}

