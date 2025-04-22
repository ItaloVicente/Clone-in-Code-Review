		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				WritableMap map = new WritableMap();
				CurrentRealm realm = (CurrentRealm) Realm.getDefault();
				boolean current = realm.isCurrent();
				realm.setCurrent(true);
				map.put("", "");
				realm.setCurrent(current);

				map.remove("");
			}
