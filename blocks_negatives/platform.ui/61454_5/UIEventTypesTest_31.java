		assertEquals(applicationElement,
				event.getProperty(UIEvents.EventTags.ELEMENT));
		assertEquals(UIEvents.ApplicationElement.ELEMENTID,
				event.getProperty(UIEvents.EventTags.ATTNAME));
		assertEquals(UIEvents.EventTypes.SET,
				event.getProperty(UIEvents.EventTags.TYPE));
