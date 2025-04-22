		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable = new ObservableStub();
				observable.fireChange();
			}
