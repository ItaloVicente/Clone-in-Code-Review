	public void testTabStateHandlerWhenSelectionChangedEvent() throws Exception {
		MPlaceholder placeHolder = AdvancedFactoryImpl.eINSTANCE
				.createPlaceholder();
		placeHolder.setRef(part);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(UIEvents.EventTags.ELEMENT, partStack);
		params.put(UIEvents.EventTags.NEW_VALUE, placeHolder);
		params.put(UIEvents.EventTags.OLD_VALUE, null);

		context.get(EventBroker.class).send(
				UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT.replace(
						UIEvents.ALL_SUB_TOPICS, UIEvents.EventTypes.SET),
