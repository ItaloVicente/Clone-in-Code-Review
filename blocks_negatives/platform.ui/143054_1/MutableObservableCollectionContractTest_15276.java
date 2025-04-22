		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.remove(delegate.createElement(collection));
			}
		}, (CurrentRealm) collection.getRealm());
