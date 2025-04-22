		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.removeAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, (CurrentRealm) collection.getRealm());
