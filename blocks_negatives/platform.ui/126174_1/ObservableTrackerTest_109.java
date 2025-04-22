		Set result = new IdentitySet(Arrays.asList(ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				ObservableTracker.setIgnore(true);
				ObservableTracker.setIgnore(false);
				ObservableTracker.setIgnore(false);
			}
