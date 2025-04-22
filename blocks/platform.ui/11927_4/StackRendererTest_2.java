		context.get(EventBroker.class).send(
				UIEvents.ApplicationElement.TOPIC_TAGS.replace(
						UIEvents.ALL_SUB_TOPICS, UIEvents.EventTypes.SET),
				params);

		assertEquals(1,
