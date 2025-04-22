		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return !wizardProgressMonitor.isVisible();
			}
		}, 10000);
