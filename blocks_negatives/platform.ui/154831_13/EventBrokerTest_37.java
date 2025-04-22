		EventHandler handler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				if (TEST_TOPIC.equals(event.getTopic())) {
					seen.incrementAndGet();
				}
