		IObservable[] result = ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.getterCalled(observable);
			}
		}, null, null);
