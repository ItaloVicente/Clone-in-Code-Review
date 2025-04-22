		IObservable[] result = ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				new ObservableStub();
				ObservableTracker.setIgnore(false);
			}
