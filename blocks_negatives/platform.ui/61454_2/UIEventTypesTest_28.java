		assertEquals(applicationElement,
				event.getProperty(UIEvents.EventTags.ELEMENT));
		assertEquals(UIEvents.ApplicationElement.TAGS,
				event.getProperty(UIEvents.EventTags.ATTNAME));
		assertEquals(UIEvents.EventTypes.REMOVE,
				event.getProperty(UIEvents.EventTags.TYPE));
