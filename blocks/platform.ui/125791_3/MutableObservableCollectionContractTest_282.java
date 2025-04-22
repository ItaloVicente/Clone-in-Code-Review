		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.add(delegate.createElement(collection));
			}
		}, (CurrentRealm) collection.getRealm());
