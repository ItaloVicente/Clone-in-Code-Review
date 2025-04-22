		childEB.subscribe(testTopic, new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				seen[0] = true;
			}
		});
