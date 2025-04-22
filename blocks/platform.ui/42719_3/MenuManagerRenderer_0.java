	@SuppressWarnings("unchecked")
	@Inject
	@Optional
	private void subscribeTopicChildAdded(@UIEventTopic(ElementContainer.TOPIC_CHILDREN) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu)) {
			return;
		}
		MMenu menuModel = (MMenu) event.getProperty(UIEvents.EventTags.ELEMENT);
		if (UIEvents.isADD(event)) {
			Object obj = menuModel;
			processContents((MElementContainer<MUIElement>) obj);
		}
	}

