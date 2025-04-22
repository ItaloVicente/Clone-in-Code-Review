		appEB.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, event -> {
			eventCount++;
			UIEventTypesTest.this.event = event;
		});
		appEB.subscribe(UIEvents.ApplicationElement.TOPIC_ELEMENTID, event -> {
			eventCount++;
			UIEventTypesTest.this.event = event;
		});
