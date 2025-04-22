	public void testTagsChangeHandlerWhenNotTagReleatedEvent() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UIEvents.EventTags.ELEMENT, part);

		context.get(IEventBroker.class).send(
				UIEvents.ApplicationElement.TOPIC_ELEMENTID.replace(
						UIEvents.ALL_SUB_TOPICS, UIEvents.EventTypes.ADD),
