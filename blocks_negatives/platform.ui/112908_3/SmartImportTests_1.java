		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return !dialog.getErrorMessage().isEmpty();
			}
		}, -1);
