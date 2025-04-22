		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.containsAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, (CurrentRealm) collection.getRealm());
