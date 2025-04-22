		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.toArray(new Object[collection.size()]);
			}
		}, (CurrentRealm) collection.getRealm());
