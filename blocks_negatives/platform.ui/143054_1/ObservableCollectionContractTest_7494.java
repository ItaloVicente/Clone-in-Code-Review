		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.hashCode();
			}
		}, (CurrentRealm) collection.getRealm());
