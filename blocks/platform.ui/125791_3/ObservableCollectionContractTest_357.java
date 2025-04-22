		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.contains(delegate.createElement(collection));
			}
		}, (CurrentRealm) collection.getRealm());
