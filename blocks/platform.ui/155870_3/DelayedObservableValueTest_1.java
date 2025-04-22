	@Test
	public void testInnerObservableNotTracked() {
		target.setValue("test");

		List<IObservable> tracked = Arrays.asList(ObservableTracker.runAndMonitor(() -> {
			delayed.getValue();
		}, null, null));

		assertFalse(tracked.contains(target));
	}

