		IObservable[] result = ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				ObservableTracker.getterCalled(observable);
				ObservableTracker.setIgnore(false);
			}
