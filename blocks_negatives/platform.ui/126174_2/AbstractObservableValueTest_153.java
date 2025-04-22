		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				ObservableValueStub observable = new ObservableValueStub();
				observable.fireValueChange(null);
			}
