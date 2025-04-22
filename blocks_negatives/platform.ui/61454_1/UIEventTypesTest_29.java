		assertEquals(applicationElement,
				event.getProperty(UIEvents.EventTags.ELEMENT));
		assertEquals(UIEvents.ApplicationElement.TAGS,
				event.getProperty(UIEvents.EventTags.ATTNAME));
		assertEquals(UIEvents.EventTypes.REMOVE_MANY,
				event.getProperty(UIEvents.EventTags.TYPE));
		assertEquals(Arrays.asList("0", "1", "2"),
				event.getProperty(UIEvents.EventTags.OLD_VALUE));
