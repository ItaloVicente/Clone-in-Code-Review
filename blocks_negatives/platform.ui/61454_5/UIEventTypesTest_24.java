		appEB.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS,
				new EventHandler() {
					@Override
					public void handleEvent(Event event) {
						eventCount++;
						UIEventTypesTest.this.event = event;
					}
				});
		appEB.subscribe(UIEvents.ApplicationElement.TOPIC_ELEMENTID,
				new EventHandler() {
					@Override
					public void handleEvent(Event event) {
						eventCount++;
						UIEventTypesTest.this.event = event;
					}
				});
