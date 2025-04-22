		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				delegate.change(observable);
			}
		}, (CurrentRealm) observable.getRealm());
