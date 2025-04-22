		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return stopButton.isEnabled();
			}
		}, 10000);
